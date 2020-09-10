package com.xu.tt.pub.hbase.example;
//package com.tarena.elearning.user.service;
//import java.util.ArrayList;
//import java.util.List;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.hadoop.hbase.client.Delete;
//import org.apache.hadoop.hbase.client.Get;
//import org.apache.hadoop.hbase.client.HTableInterface;
//import org.apache.hadoop.hbase.client.Result;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.springframework.stereotype.Service;
//
//import com.tarena.elearning.base.LearningPut;
//import com.tarena.elearning.businessschool.model.StudentJob;
//import com.tarena.elearning.user.exception.PasswordException;
//import com.tarena.elearning.user.exception.UserException;
//import com.tarena.elearning.user.model.InterResult;
//import com.tarena.elearning.user.model.StudentModel;
//import com.tarena.elearning.user.model.User;
//import com.tarena.elearning.user.thread.EProducer;
//import com.tarena.elearning.util.Constants;
//import com.tarena.elearning.util.InterUtil;
//import com.tarena.elearning.util.MD5Util;
//import com.tarena.elearning.web.JedisUtil;
//import com.tarena.elearning.web.LearningWeb;
//
//@Service("userService")
//public class UserServiceImpl implements UserService {
//
//	@Override
//	public User login(User user) throws PasswordException, UserException, Exception  {
//		String l_password = user.getPassword() ;
//		User l_user = this.findUserByLoginName(user.getLogin_name());
//		if( l_user == null ) throw new UserException("用户名错误");
//		if( l_password !=null && l_password.equals(l_user.getPassword()) ){
//			return l_user ;
//		} else {
//			throw new PasswordException("密码错误");
//		}
//	}
//	
//	@Override
//	public void addUser( User user) throws UserException , Exception {
//		try{
//			//1.验证用户名是否重复
//			String redis_user = 
//				JedisUtil.getValue( JedisUtil.E_user , user.getLogin_name() );
//			if( redis_user!=null && redis_user.length()>0 ) 
//				throw new UserException("用户名重复");
//			//2.加入到队列HBase添加用户，
//			//UserThreadQueue.addUser.put(user);
//		}catch( UserException e ){
//			throw new UserException( e.getMessage() );
//		}
//	}
//	@Override
//	public void addUserEmail(User user) throws Exception {
//		try{
//			//1.验证用户名是否重复
//			String redis_user =  JedisUtil.getValue( JedisUtil.E_user , user.getLogin_name() );
//			if( redis_user!=null && redis_user.length()>0 ) 
//				throw new UserException("用户名重复");
//			//1.1 若不重复 先放入redis缓存中,再讲放入队列中,发送邮件以及存入Hbase中
//			JedisUtil.setValue(JedisUtil.E_user, user.getLogin_name(),user.redisValue());
//			//2.加入到队列HBase添加用户，
//			EProducer.sendRegister(user.getLogin_name()+"="+user.redisValue());
//		}catch( UserException e ){
//			throw new UserException( e.getMessage() );
//		}
//	}
//	@Override
//	public User activate(String mail) throws UserException {
//		User user =null;
//		try{
//			user = this.findUserByLoginName("P#"+mail);
//			if(user.getEmail()!=null&&user.getEmail().equals(mail))
//				throw new UserException("账号已激活");
//			HTableInterface table = LearningWeb.HBASE_POOL.getTable("user");
//			table.setAutoFlush(false);
//			table.setWriteBufferSize(LearningWeb.HBASE_WRITE_BUFFER);
//			LearningPut t_put = new LearningPut( "P#"+mail );
//			t_put.add( "info" , "email" , String.valueOf(mail) );
//			table.put( t_put.getPut() );
//			table.flushCommits();
//			table.close();
//		}catch( UserException e ){
//			throw e;
//		}catch( Exception e ){
//			e.printStackTrace();
//			throw new UserException("HBase错误");
//		}
//		//修改redis
//		try{
//			user.setEmail(mail);//激活用户
//			JedisUtil.setValue( JedisUtil.E_user , user.getLogin_name() , user.redisValue() );
//			return user;
//		}catch( Exception e ){
//			e.printStackTrace();
//		}
//		return null;
//	}
//	/**
//	 * 更新用户   Add by YHT
//	 */
//	@Override
//	public void updateUserOutTime( User user ) throws UserException , Exception {
//		try{
//			HTableInterface table = LearningWeb.HBASE_POOL.getTable("user");
//			table.setAutoFlush(false);
//			table.setWriteBufferSize(LearningWeb.HBASE_WRITE_BUFFER);
//			LearningPut t_put = new LearningPut( user.getLogin_name() );
//			t_put.add( "info" , "outtime" , String.valueOf(user.getOuttime().getTime()) );
//			if(user.getTts_outtime()!=null){
//				t_put.add( "info" , "tts_outtime" , String.valueOf(user.getTts_outtime().getTime()) );
//			}
//			t_put.add( "info" , "vip" , String.valueOf(user.getVip()) );
//			table.put( t_put.getPut() );
//			table.flushCommits();
//			table.close();
//		}catch( Exception e ){
//			e.printStackTrace();
//			throw new UserException("HBase错误");
//		}
//		//修改redis
//		try{
//			JedisUtil.setValue( JedisUtil.E_user , user.getLogin_name() , user.redisValue() );
//		}catch( Exception e ){
//			e.printStackTrace();
//		}
//	}
//
//	
//	@Override
//	public void updateUser(String loginName, String type, String value)
//			throws Exception {
//		if( !"name".equals(type)  && !"telphone".equals(type) && 
//			!"email".equals(type) && !"outtime".equals(type)  &&
//			!"user_mima".equals(type)){
//			return ;
//		}
//		if( "user_mima".equals(type) ) type="password";
//		//1. 更新HBase
//		HTableInterface table = LearningWeb.HBASE_POOL.getTable("user");
//		table.setAutoFlush(false); 
//		table.setWriteBufferSize(LearningWeb.HBASE_WRITE_BUFFER);
//		LearningPut t_put = new LearningPut( loginName );
//		t_put.add( "info" , type , value );
//		table.put( t_put.getPut() );
//		table.flushCommits();
//		table.close();
//		//2. 更新Redis
//		String u_value = JedisUtil.getValue( JedisUtil.E_user , loginName );
//		if( u_value == null || u_value.length()<1 ) return  ;
//		User user = new User();
//		user.setLogin_name(loginName);
//		user.redisValue( u_value );
//		if("name".equals(type)){
//			user.setName(value );	
//		}else if("telphone".equals(type)){
//			user.setTelphone(value);
//		}else if("email".equals(type)){
//			user.setEmail(value);
//		}else if("password".equals(type)){
//			user.setPassword(value);
//		}
//		JedisUtil.setValue( JedisUtil.E_user , user.getLogin_name() , user.redisValue() );
//	}
//	
//	@Override
//	public String findLoginNameBySessionId(String sessionId) throws Exception {
//		String login_name = 
//			JedisUtil.getValue( JedisUtil.E_login, sessionId );
//		return login_name ;
//	}
//	
//	@Override
//	public User findUserBySessionId(String sessionId) throws Exception {
//		try {
//			String login_name = this.findLoginNameBySessionId(sessionId);
//			if( login_name == null || login_name.length()<1 ) return null ;
//			return this.findUserByLoginName( login_name );
//		} catch ( Exception e) {
//			throw new UserException("REDIS错误");
//		}
//	}
//
//	@Override
//	public User findUserByLoginName(String loginName) throws Exception {
//		try {
//			System.out.println("1.1"+loginName);
//			//1. 从 redis中取出
//			String u_value = JedisUtil.getValue( JedisUtil.E_user, loginName);
//			
//			if( u_value != null ){
//				User user = new User();
//				user.setLogin_name( loginName );
//				user.redisValue( u_value );
//				return user ;
//			}
//			throw new Exception("redis无数据");
//		} catch (Exception e) {
//			//2. redis中不存在，从HBase取出
//			System.out.println("1.2");
//			HTableInterface table = LearningWeb.HBASE_POOL.getTable("user");
//			Get get = new Get( Bytes.toBytes(loginName) );
//			Result row = table.get(get);
//			if( row.isEmpty() ) return null ;
//			User l_user = new User( row );
//			//3. 存入redis
//			try {
//				JedisUtil.setValue( JedisUtil.E_user , l_user.getLogin_name() , l_user.redisValue() );
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			return l_user ;
//		}
//	}
//
//	@Override
//	public void updatePassword(String loginName, String password) throws UserException{
//		try {
//			//1. 更新hbase
//			HTableInterface table = LearningWeb.HBASE_POOL.getTable("user");
//			table.setAutoFlush(false);
//			table.setWriteBufferSize(LearningWeb.HBASE_WRITE_BUFFER);
//			LearningPut t_put = new LearningPut( loginName );
//			t_put.add( "info" , "password" , MD5Util.encodeString(password) );
//			table.put( t_put.getPut() );
//			table.flushCommits();
//			table.close();
//			//2. 清除redis
//			try {
//				String u_value = JedisUtil.getValue( JedisUtil.E_user , loginName );
//				if( u_value == null || u_value.length()<1 ) return  ;
//				User user = new User();
//				user.setLogin_name(loginName);
//				user.redisValue( u_value );
//				user.setPassword( MD5Util.encodeString(password) );
//				JedisUtil.setValue(JedisUtil.E_user , loginName , user.redisValue());
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//		} catch (Exception e) {
//			throw new UserException("更新失败");
//		}
//	}
//	@Override
//	public void updateImage(User user,String fname) throws Exception {
//		try {
//			//1. 更新hbase
//			HTableInterface table = LearningWeb.HBASE_POOL.getTable("user");
//			table.setAutoFlush(false);
//			table.setWriteBufferSize(LearningWeb.HBASE_WRITE_BUFFER);
//			LearningPut t_put = new LearningPut( user.getLogin_name() );
//			t_put.add( "info" , "img" , fname );
//			table.put( t_put.getPut() );
//			table.flushCommits();
//			table.close();
//			//2. 清除redis
//			try {
//				user.setImg(fname);
//				JedisUtil.setValue(JedisUtil.E_user , user.getLogin_name() , user.redisValue());
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//		} catch (Exception e) {
//			throw new UserException("更新失败");
//		}
//	}
//	@Override
//	public void deleteUser(String loginName) throws UserException {
//		try {
//			//1. 清除redis
//			JedisUtil.delValue( JedisUtil.E_user , loginName );
//			//2. 删除hbase
//			try{
//				HTableInterface table = LearningWeb.HBASE_POOL.getTable("user");
//				Delete del = new Delete( loginName.getBytes() );  
//				table.delete( del );
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new UserException("删除失败");
//		}
//	}
//
//	@Override
//	public Integer getUserLoginTimes(String loginName) throws Exception {
//		String value = JedisUtil.getValue(JedisUtil.VALIDTIMES ,  loginName);
//		Integer times = 0;
//		if(value != null){
//			try {
//				times = Integer.parseInt(value);
//			} catch (Exception e) {
//				times = 0;
//				e.printStackTrace();
//			}
//		}
//		return times;
//	}
//
//	@Override
//	public Integer setUserLoginTimes(String loginName,Integer times) throws Exception {
//		JedisUtil.setValueTime(JedisUtil.VALIDTIMES , loginName, String.valueOf(times), 120);
//		return times;
//	}
//
//	@Override
//	public List<InterResult> converStudent(StudentModel studentModel) throws Exception {
//		List <StudentModel> list = new ArrayList<StudentModel>();list.add(studentModel);
//		String type=Constants.OPERATION_TYPE_CONVER;
//		List<InterResult> resultList =  null;
//		//1.将list对象转为json字符串
//		String listStr = JSONArray.fromObject(list).toString();
//		//2.拼参数
//		//2.1(1.数据+公钥+私钥  三部分加密成密文，用md5加密；2.公钥+密文  以参数形式拼到JSON里面)
//		MD5Util.init();
//		String pwd=listStr+Constants.PUBLIC_KEY+Constants.PRIVATE_KEY;
//		String siphertext=MD5Util.md5Util(pwd,"md5");
//		//System.out.println(pwd+"\t"+siphertext);
//		listStr+=",'publicKey':'"+Constants.PUBLIC_KEY+"','siphertext':'"+siphertext+"'";
//		//2.2(拼JSON)
//		String param = "{'action':'"+type+"','students':"+listStr+"}";
//		System.out.println(param);
//		//3.发送数据
//		String resultStr = InterUtil.sendMessage(Constants.STUDENT,param);
//		//4.获得结果
//		JSONArray jsonArray = JSONArray.fromObject(resultStr);
//		//resultList =jsonArray.toList(jsonArray,InterResult.class);
//		resultList=(List<InterResult>) jsonArray.toCollection(jsonArray, InterResult.class);
//		return resultList;
//	}
//
//	@Override
//	public List<InterResult> activeStudent(StudentModel studentModel) throws Exception {
//		List <StudentModel> list = new ArrayList<StudentModel>();list.add(studentModel);
//		String type=Constants.OPERATION_TYPE_ACTIVE;
//		List<InterResult> resultList =  null;
//		//1.将list对象转为json字符串
//		String listStr = JSONArray.fromObject(list).toString();
//		//2.拼参数
//		//2.1(1.数据+公钥+私钥  三部分加密成密文，用md5加密；2.公钥+密文  以参数形式拼到JSON里面)
//		MD5Util.init();
//		String pwd=listStr+Constants.PUBLIC_KEY+Constants.PRIVATE_KEY;
//		String siphertext=MD5Util.md5Util(pwd,"md5");
//		//System.out.println(pwd+"\t"+siphertext);
//		listStr+=",'publicKey':'"+Constants.PUBLIC_KEY+"','siphertext':'"+siphertext+"'";
//		//2.2(拼JSON)
//		String param = "{'action':'"+type+"','students':"+listStr+"}";
//		System.out.println(param);
//		//3.发送数据
//		String resultStr = InterUtil.sendMessage(Constants.STUDENT,param);
//		//4.获得结果
//		JSONArray jsonArray = JSONArray.fromObject(resultStr);
//		resultList =jsonArray.toList(jsonArray,InterResult.class);
//		return resultList;
//	}
//
//	@Override
//	public List<InterResult> updatePhone(StudentModel studentModel) throws Exception {
//		List <StudentModel> list = new ArrayList<StudentModel>();list.add(studentModel);
//		String type=Constants.OPERATION_TYPE_UPDATEPHONE;
//		List<InterResult> resultList =  null;
//		//1.将list对象转为json字符串
//		String listStr = JSONArray.fromObject(list).toString();
//		//2.拼参数
//		//2.1(1.数据+公钥+私钥  三部分加密成密文，用md5加密；2.公钥+密文  以参数形式拼到JSON里面)
//		MD5Util.init();
//		String pwd=listStr+Constants.PUBLIC_KEY+Constants.PRIVATE_KEY;
//		String siphertext=MD5Util.md5Util(pwd,"md5");
//		//System.out.println(pwd+"\t"+siphertext);
//		listStr+=",'publicKey':'"+Constants.PUBLIC_KEY+"','siphertext':'"+siphertext+"'";
//		//2.2(拼JSON)
//		String param = "{'action':'"+type+"','students':"+listStr+"}";
//		System.out.println(param);
//		//3.发送数据
//		String resultStr = InterUtil.sendMessage(Constants.STUDENT,param);
//		//4.获得结果
//		JSONArray jsonArray = JSONArray.fromObject(resultStr);
//		resultList =(List<InterResult>) jsonArray.toCollection(jsonArray,InterResult.class);
//		return resultList;
//	}
//
//	@Override
//	public List<InterResult> courseChange(StudentModel studentModel) throws Exception {
//		List <StudentModel> list = new ArrayList<StudentModel>();list.add(studentModel);
//		String type="COURSECHANGE";
//		List<InterResult> resultList =  null;
//		//1.将list对象转为json字符串
//		String listStr = JSONArray.fromObject(list).toString();
//		//2.拼参数
//		//2.1(1.数据+公钥+私钥  三部分加密成密文，用md5加密；2.公钥+密文  以参数形式拼到JSON里面)
//		MD5Util.init();
//		String pwd=listStr+Constants.PUBLIC_KEY+Constants.PRIVATE_KEY;
//		String siphertext=MD5Util.md5Util(pwd,"md5");
//		//System.out.println(pwd+"\t"+siphertext);
//		listStr+=",'publicKey':'"+Constants.PUBLIC_KEY+"','siphertext':'"+siphertext+"'";
//		//2.2(拼JSON)
//		String param = "{'action':'"+type+"','students':"+listStr+"}";
//		System.out.println(param);
//		//3.发送数据
//		String resultStr = InterUtil.sendMessage(Constants.STUDENT,param);
//		//4.获得结果
//		JSONArray jsonArray = JSONArray.fromObject(resultStr);
//		resultList =(List<InterResult>) jsonArray.toCollection(jsonArray, InterResult.class);
//		return resultList;
//	}
//
//	@Override
//	public void addUserPhone(User user) throws Exception {
//		try{
//			//1.验证用户名是否重复
//			System.out.println("注册手机用户："+user.getLogin_name());
//			String redis_user =  JedisUtil.getValue( JedisUtil.E_user , user.getLogin_name() );
//			if( redis_user!=null && redis_user.length()>0 ) {
//				System.out.println("重复账号信息： "+redis_user);
//				throw new UserException("账号重复");
//			}
//			try{
//				HTableInterface table = LearningWeb.HBASE_POOL.getTable("user");
//				table.setAutoFlush(false);
//				table.setWriteBufferSize(LearningWeb.HBASE_WRITE_BUFFER);
//			//1.验证用户名是否重复
//				Get get = new Get(user.getLogin_name().getBytes());
//				Result row = table.get(get);
//				if( !row.isEmpty() ) throw new UserException("rowkey重复");
//			//2.HBase添加用户
//				LearningPut t_put = new LearningPut( user.getLogin_name() );
//				t_put.add( "info" , "password" , user.getPassword() );
//				t_put.add( "info" , "is_tts" , String.valueOf(user.getIs_tts()) );
//				t_put.add( "info" , "name" , user.getName() );
//				t_put.add( "info" , "create_time" , String.valueOf(user.getCreate_time().getTime()) );
//				t_put.add( "info" , "outtime" , "0" );
//				table.put( t_put.getPut() );
//				table.flushCommits();
//				table.close();
//			}catch( UserException e ){
//				throw new UserException( e.getMessage() );
//			}catch( Exception e ){
//				e.printStackTrace();
//				throw new UserException("HBase错误");
//			}
//			//1.1 若不重复 先放入redis缓存中,再讲放入队列中,发送邮件以及存入Hbase中
//			JedisUtil.setValue(JedisUtil.E_user, user.getLogin_name(),user.redisValue());
//		}catch( UserException e ){
//			throw new UserException( e.getMessage() );
//		}
//		
//	}
//
//	@Override
//	public void updateUserPhone(String loginID, String type, String value) {
//		try {
//			StudentJob studentJob =findTBSUserByLoginID(loginID);
//			if ("realName".equals(type)) {
//				studentJob.setName(value);
//			}else if ("corporation".equals(type)) {
//				studentJob.setCorporation(value);
//			}else if("position".equals(type)) {
//				studentJob.setPosition(value);
//			}else if ("workYears".equals(type)) {
//				studentJob.setWorkYears(value);
//			}else if ("city".equals(type)) {
//				studentJob.setCity(value);
//			}
//		
//			JSONObject object=JSONObject.fromObject(studentJob);
//			JedisUtil.setValue(JedisUtil.TBS_USER_PERFECT, loginID, object.toString());
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//
//	@Override
//	public StudentJob findTBSUserByLoginID(String loginID)throws Exception {
//		// TODO Auto-generated method stub
//		StudentJob studentJob ;
//		System.out.println("----获取个人信息---"+loginID);
//		try {
//			String value=JedisUtil.getValue(JedisUtil.TBS_USER_PERFECT, loginID);
//			if (value==null) {
//				return new StudentJob();
//			}
//			JSONObject jsonObject = JSONObject.fromObject(value);
//			 studentJob = (StudentJob)JSONObject.toBean(jsonObject,StudentJob.class);
//			return studentJob;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return new StudentJob() ;
//		}
//		
//		
//	}
//	
//}