package com.xu.tt.pub.hbase.example;
//package com.tarena.elearning.user.model;
//
//import java.util.Date;
//
//import org.apache.hadoop.hbase.client.Result;
//
//import com.tarena.elearning.base.LearningRow;
//import com.tarena.elearning.util.MD5Util;
//import com.tarena.elearning.util.RayDateUtils;
//
//public class User {
//
//	private String row_key ;//P=个人用户  E=企业用户
//	private String login_name ;
//	private String password ;
//	private Date   outtime ;
//	private int    is_tts ;//是否TTS用户 1=是 0=否   // 0 =tmooc用户 1=tts 2=童程 3=童创 4=童美  5=商学院
//	private Date   create_time ;//注册时间
//	private String email ;
//	private String name ;//昵称
//	private String telphone ;//手机
//	private int sex;  //性别
//	private String img; // 头像
//	private Date tts_outtime;//tts_vip过期时间
//	private int vip;  //购买vip 类型
//	private int isupdatepwd;//是否需要修改密码    1 是   0 否
//	
//	public User(){
//	}
//	
//	public User( Result row ){
//		if( row.isEmpty() ) return ;
//		this.row_key = new String( row.getRow() );
//		this.login_name = this.row_key ;
//		this.password = LearningRow.getString( row, "info", "password" );
//		this.outtime =  LearningRow.getDate( row, "info", "outtime" );
//		try {
//			this.is_tts =  LearningRow.getInteger( row, "info", "is_tts" );
//		} catch (Exception e1) {
//			this.is_tts =  1;
//		}
//		this.create_time = LearningRow.getDate( row, "info", "create_time" );
//		this.email = LearningRow.getString( row, "info", "email" );
//		this.name = LearningRow.getString( row, "info", "name" );
//		this.telphone = LearningRow.getString( row, "info", "telphone" );
//		try{
//			this.sex =  LearningRow.getInteger( row, "info", "sex" );
//		}catch(Exception e){
//			this.sex = 1;
//		}
//		try{
//			this.img = LearningRow.getString( row, "info", "img" );
//		}catch(Exception e){
//			this.img = "";
//		}
//		try{
//			this.tts_outtime = LearningRow.getDate(row, "info", "tts_outtime");
//		}catch(Exception e){
//			this.tts_outtime =  new Date();
//		}
//		try{
//			this.vip = LearningRow.getInteger(row, "info", "vip");
//		}catch(Exception e){
//			this.vip =  0;
//		}
//		
//	}
//	
//	public String pOre() {
//		try{
//			String pe = this.login_name.split("#")[0];
//			if( !"E".equals(pe) || !"P".equals(pe) ){
//				return null ;
//			}
//			return pe ;
//		} catch(Exception e ){
//			return null ;
//		}
//	}
//
//	/**
//	 * 获取剩余过期天数
//	 * */
//	public long outDays(){
//		if( this.outtime == null ) return 0 ;
//		Date date = new Date();
//		if( date.after( this.outtime) )return 0 ;
//		return RayDateUtils.getDaysBetween( this.outtime , date );
//	}
//	/**
//	 * "Redis存储格式：E-USER（E数据库，USER表）
//		key   - login_name
//		value - password|outtime|is_tts|create_time|email|name|telphone|sex|img|tts_outtime|vip"
//	 * */
//	public void redisValue( String value ){
//		String[] v = value.split("\\|");
//		this.row_key = this.login_name ;
//		this.password = v[0];
//		this.outtime= new Date( Long.valueOf(v[1]) );
//		this.is_tts= Integer.valueOf(v[2]);
//		this.create_time= new Date( Long.valueOf(v[3]) );
//		this.email= v[4];
//		this.name= v[5];
//		this.telphone= v.length>6 ? v[6] :"";
//		this.sex= v.length>7 ? Integer.valueOf(v[7]) :0;
//		this.img= v.length>8 ? v[8] :"";
//		this.tts_outtime= v.length>9 ? (v[9]!=null&&v[9].length()>0)?new Date(Long.valueOf(v[9])):null:null;
//		this.vip= v.length>10 ? v[10]!=null?Integer.valueOf(v[10]):0:0;
//	}
//	/**
//	 * "Redis存储格式：E-USER（E数据库，USER表）
//		key   - login_name
//		value - password|outtime|is_tts|create_time|email|name|telphone|sex|img|tts_outtime|vip"
//	 * */
//	public String redisValue(){
//		StringBuffer s = new StringBuffer();
//		s.append(this.password);
//		s.append("|");
//		s.append(this.outtime==null?"0":this.outtime.getTime());
//		s.append("|");
//		s.append(this.is_tts);
//		s.append("|");
//		s.append(this.create_time==null?"0":this.create_time.getTime());
//		s.append("|");
//		s.append(this.email==null ? "" : this.email);
//		s.append("|");
//		s.append(this.name == null ? "" : this.name );
//		s.append("|");
//		s.append(this.telphone == null ? "" : this.telphone );
//		s.append("|");
//		s.append(this.sex);
//		s.append("|");
//		s.append(this.img == null ? "" : this.img );
//		s.append("|");
//		s.append(this.tts_outtime == null ? "" : this.tts_outtime.getTime() );
//		s.append("|");
//		s.append(this.vip);
//		return s.toString() ;
//	}
//	
//	public String cookieValue( String uuid ){
//		//System.out.println("this.getCreate_time():"+this.getCreate_time());
//		return 
//			uuid+"|"+this.getLogin_name()+"|"+this.getName()+"|"
//				+this.getIs_tts()+"|"+this.outDays()+"|"+this.getSex() + "|" + this.getImg()+"|" + this.vip+"|" + this.getCreate_time().getTime()+"|" + this.getIsupdatepwd();
//	}
//	public int getVip() {
//		return vip;
//	}
//	public void setVip(int vip) {
//		this.vip = vip;
//	}
//	public Date getTts_outtime() {
//		return tts_outtime;
//	}
//
//	public void setTts_outtime(Date tts_outtime) {
//		this.tts_outtime = tts_outtime;
//	}
//
//	public String getRow_key() {
//		return row_key;
//	}
//	public void setRow_key(String rowKey) {
//		row_key = rowKey;
//	}
//	public String getLogin_name() {
//		return login_name;
//	}
//	public void setLogin_name(String loginName) {
//		login_name = loginName;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public Date getOuttime() {
//		return outtime;
//	}
//	public void setOuttime(Date outtime) {
//		this.outtime = outtime;
//	}
//	public int getIs_tts() {
//		return is_tts;
//	}
//	public void setIs_tts(int isTts) {
//		is_tts = isTts;
//	}
//	public Date getCreate_time() {
//		return create_time;
//	}
//	public void setCreate_time(Date createTime) {
//		create_time = createTime;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getTelphone() {
//		return telphone;
//	}
//	public void setTelphone(String telphone) {
//		this.telphone = telphone;
//	}
//	public int getSex() {
//		return sex;
//	}
//
//	public void setSex(int sex) {
//		this.sex = sex;
//	}
//
//	public String getImg() {
//		return img;
//	}
//
//	public void setImg(String img) {
//		this.img = img;
//	}
//	
//	public int getIsupdatepwd() {
//		if(this.is_tts==1 && MD5Util.encodeString("123456").equals(this.getPassword())){
//			return 1;
//		}
//		return 0;
//	}
//
//	public void setIsupdatepwd(int isupdatepwd) {
//		this.isupdatepwd = isupdatepwd;
//	}
//
//	@Override
//	public String toString() {
//		return "User [create_time=" + create_time + ", email=" + email
//				+ ", is_tts=" + is_tts + ", login_name=" + login_name
//				+ ", name=" + name + ", outtime=" + outtime + ", password="
//				+ password + ", row_key=" + row_key + ", telphone=" + telphone
//				+ "]";
//	}
//	
//}
