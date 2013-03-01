package org.bear.identity.test;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.avro.AvroRemoteException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.map.HashedMap;
import org.bear.api.identity.Credential;
import org.bear.api.identity.CredentialType;
import org.bear.api.identity.IdentityService;
import org.bear.api.identity.User;
import org.bear.api.identity.UserStatus;
import org.bear.api.type.Gender;
import org.bear.api.type.GlobalException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-2-28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test.xml")
public class IdentityServiceTest {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IdentityService identityService;
	
	private static final ThreadLocal<MultiValueMap<String, Object>> CONTEXT = new ThreadLocal<MultiValueMap<String, Object>>() {

		@Override
		protected MultiValueMap<String, Object> initialValue() {
			return new LinkedMultiValueMap<String, Object>();
		}

	};

	protected String createName() {
		Random rnd = (Random) CONTEXT.get().getFirst("random");
		byte[] ba = new byte[6];
		rnd.nextBytes(ba);
		return Hex.encodeHexString(ba);
	}

	protected User createUser() throws Exception {
		User user = new User();
		Random rnd = (Random) CONTEXT.get().getFirst("random");
		String rndStr = this.createName();
		user.setUserName("UN_" + rndStr);
		user.setNickName("NN_" + rndStr);
		user.setRealName("RN_" + rndStr);
		user.setGender(Gender.values()[rnd.nextInt(3)]);
		user.setIdNo("32000000000000000");
		user.setLocation("000000");
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse("2012-09-06");
		user.setBirthday(d.getTime());
		user.setDescription(rndStr);
		user.setPhoto("photo-" + rndStr);
		user.setStatus(UserStatus.NORMAL);
		return user;
	}

	protected void updateUser(User user) throws Exception {
		Random rnd = (Random) CONTEXT.get().getFirst("random");
		String rndStr = this.createName();
		user.setUserName("UN_" + rndStr);
		user.setNickName("NN_" + rndStr);
		user.setRealName("RN_" + rndStr);
		user.setGender(Gender.values()[rnd.nextInt(3)]);
		user.setIdNo("32000000000000000");
		user.setLocation("000000");
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01");
		user.setBirthday(d.getTime());
		user.setDescription(rndStr);
		user.setPhoto("photo-" + rndStr);
	}

	protected String createPassword() {
		Random rnd = (Random) CONTEXT.get().getFirst("random");
		byte[] ba = new byte[6];
		rnd.nextBytes(ba);
		return Hex.encodeHexString(ba);
	}

	protected CredentialType createType() {
		Random rnd = (Random) CONTEXT.get().getFirst("random");
		return CredentialType.values()[rnd.nextInt(4)];
	}

	@Test
	public void initContext() {
		CONTEXT.get().set("random", new Random());
	}
	
	/**
	 * 测试完整用户注册信息
	 */
	@Test
	public void testRegisterUser() throws Exception {
		try {
			User user = this.createUser();
			CONTEXT.get().set("user", user);

			Credential credential = new Credential();
			credential.setType(this.createType());
			credential.setName(this.createName());
			credential.setValue(this.createPassword());
			CONTEXT.get().set("credential", credential);
			
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("attr1", "fuck u");
			String token = this.identityService.registerUser(user, credential,
					attributes);

			CONTEXT.get().set("token", token);

			long uid = this.identityService.validateToken(token);
			user.setId(uid);

			User serviceUser = this.identityService.getUser(uid);

			this.assertEqualUser(user, serviceUser);

			List<Credential> userCredentials = this.identityService.getUserCredentials(uid);
			Assert.assertEquals(1, userCredentials.size());

			Credential userCredential = userCredentials.get(0);
			//Assert.assertEquals(uid, userCredential.getUid());
			Assert.assertEquals(credential.getType(), userCredential.getType());
			Assert.assertEquals(credential.getName(), userCredential.getName());

		} catch (GlobalException e) {
			logger.info("ERROR: " + e.getCode());
		}
	}
	
	@Test
	public void getUser() throws Exception{
		Long uid = 1l;
		User serviceUser = this.identityService.getUser(uid);
		System.out.println(serviceUser.getNickName());
	}
	
	protected void assertEqualUser(User expected, User actual) throws Exception {
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getUserName(), actual.getUserName());
		Assert.assertEquals(expected.getNickName(), actual.getNickName());
		Assert.assertEquals(expected.getRealName(), actual.getRealName());
		Assert.assertEquals(expected.getGender(), actual.getGender());
		Assert.assertEquals(expected.getIdNo(), actual.getIdNo());
		Assert.assertEquals(expected.getLocation(), actual.getLocation());
		Assert.assertEquals(expected.getBirthday(), actual.getBirthday());
		Assert.assertEquals(expected.getDescription(), actual.getDescription());
		Assert.assertEquals(expected.getPhoto(), actual.getPhoto());
	}
}
