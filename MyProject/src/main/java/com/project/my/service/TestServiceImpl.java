package com.project.my.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.project.my.bean.TestBean;
import com.project.my.dao.TestDao;
import com.project.my.bean.MemberDto;

@Service
public class TestServiceImpl implements TestService {
	@Inject
	private TestDao dao;

	private String secretKey = "7YyM7J28IOyVlO2YuO2ZlCDtgqTqsJLsnoXri4jri6Qh";

	@Override
	@Transactional(readOnly = true)
	public List<TestBean> selectTest(int page, int pageSize) throws Exception {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("page", page);
		param.put("size", pageSize);

		return dao.selectTest(param);
	}

	@Override
	@Transactional(readOnly = true)
	public TestBean detailTest(Long id) throws Exception {
		return dao.detailTest(id);
	}

	@Transactional
	public void insertTest(MultipartHttpServletRequest request, String title, String content, MemberDto memberDto)
			throws Exception {

//		String secretKey = "7YyM7J28IOyVlO2YuO2ZlCDtgqTqsJLsnoXri4jri6Qh";

		Map<String, String> param = new HashMap<String, String>();
		param.put("title", title);
		param.put("content", content);
		param.put("writer", memberDto.getName());

		// ������ ������ ���
		String path = "C:/AttachedFile";

		MultipartFile upload = request.getFile("file");

		if (!upload.isEmpty()) {
			String name = upload.getOriginalFilename();
			UUID uuid = UUID.randomUUID();
			String secretName = encodingAES256(name, secretKey);
			String base64Encode = Base64.getEncoder().encodeToString(secretName.getBytes());

			String savedFileName = uuid.toString() + "aes256" + base64Encode + ".jpg";
			// param�� ���� ��� ����
			param.put("route", "/img" + File.separator + savedFileName);
			param.put("img", name);

			// ������ ������ ��ġ �¾�
			// ���ε� ó��!
			File dest = new File(path + File.separator, savedFileName);
			upload.transferTo(dest);
		}

		dao.insertTest(param);
	}

	@Override
	@Transactional(readOnly = true)
	public void downloadTest(HttpServletResponse response, Long id) throws Exception {
		TestBean testBeanData = dao.detailTest(id);

		String fileSecretName = testBeanData.getRoute().split("img")[1];

		System.out.println(fileSecretName);

		String path = "C:" + File.separator + "AttachedFile" + fileSecretName;

		System.out.println(path);

		File file = new File(path);
		// �ٿ�ε� �ǰų� ���ÿ� ����Ǵ� �뵵�� ���̴����� �˷��ִ� ���
		response.setHeader("Content-Disposition",
				"attachment;filename=\"" + new String(testBeanData.getImg().getBytes("UTF-8"), "ISO-8859-1") + "\"");

		FileInputStream fileInputStream = new FileInputStream(path); // ���� �о����
		OutputStream out = response.getOutputStream();

		try (FileInputStream fis = new FileInputStream(path)) {
			// saveFileName�� �Ķ���ͷ� �־� inputStream ��ü�� �����
			// response���� ������ ������ OutputStream�� �����ͼ�
			int readCount = 0;
			byte[] buffer = new byte[1024];
			// ���� ���� ��ŭ ũ���� buffer�� ������ ��
			while ((readCount = fis.read(buffer)) != -1) {
				out.write(buffer, 0, readCount);
				// outputStream�� �����ش�
			}
		} catch (Exception ex) {
			throw new RuntimeException("file Load Error");
		}
	}

	@Override
	@Transactional
	public void updateTest(Long id, String title) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("title", title);

		dao.updateTest(param);
	}

	@Override
	@Transactional
	public void deleteTest(Long id) throws Exception {
		dao.deleteTest(id);
	}

	@Override
	@Transactional
	public void signupTest(MemberDto memberDto) throws Exception {

		MemberDto member = dao.checkTest(memberDto.getEmail());

		if (member == null) {
			throw new IllegalArgumentException("�ߺ��� ���̵��Դϴ�.");
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("email", memberDto.getEmail());
		param.put("password", encodingSha256(memberDto.getPassword()));
		param.put("name", memberDto.getName());

		dao.signupTest(param);
	}

	@Override
	@Transactional
	public MemberDto loginTest(String id, String password) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("email", id);
		param.put("password", encodingSha256(password));

		System.out.println(id + encodingSha256(password));

		MemberDto memberData = dao.loginTest(param);

		System.out.println("testlogin");
		System.out.println(memberData.getEmail() + memberData.getName());

		return memberData;
	}

	public static String encodingAES256(String msg, String key) throws Exception {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		byte[] saltBytes = bytes;

		// Password-Based Key Derivation function 2
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

		// 70000�� �ؽ��Ͽ� 256 bit ������ Ű�� �����.
		PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltBytes, 70000, 256);

		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

		// �˰���/���/�е�
		// CBC : Cipher Block Chaining Mode
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();

		// Initial Vector(1�ܰ� ��ȣȭ ��Ͽ�)
		byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();

		byte[] encryptedTextBytes = cipher.doFinal(msg.getBytes("UTF-8"));

		byte[] buffer = new byte[saltBytes.length + ivBytes.length + encryptedTextBytes.length];
		System.arraycopy(saltBytes, 0, buffer, 0, saltBytes.length);
		System.arraycopy(ivBytes, 0, buffer, saltBytes.length, ivBytes.length);
		System.arraycopy(encryptedTextBytes, 0, buffer, saltBytes.length + ivBytes.length, encryptedTextBytes.length);

		return Base64.getEncoder().encodeToString(buffer);
	}

	public static String decodingAES256(String msg, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(msg));

		byte[] saltBytes = new byte[20];
		buffer.get(saltBytes, 0, saltBytes.length);
		byte[] ivBytes = new byte[cipher.getBlockSize()];
		buffer.get(ivBytes, 0, ivBytes.length);
		byte[] encryoptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes.length];
		buffer.get(encryoptedTextBytes);

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltBytes, 70000, 256);

		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));

		byte[] decryptedTextBytes = cipher.doFinal(encryoptedTextBytes);
		return new String(decryptedTextBytes);
	}

	public static String encodingSha256(String planText) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(planText.getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
