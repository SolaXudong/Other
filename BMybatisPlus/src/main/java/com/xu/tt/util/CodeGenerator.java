package com.xu.tt.util;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * @author XuDong 2020-11-29 20:29:34
 * @tips 使用参考：https://baomidou.com
 * @tips 使用说明：<br>
 *       1.tables-表名用逗号隔开<br>
 *       2.packageName-包路径<br>
 *       3.数据库地址<br>
 *       注：默认生成Dto、Dao，如需Service、Controller，NULL处注掉，如需XML，解开注释<br>
 */
@SuppressWarnings({ "unused" })
public class CodeGenerator {

	private static String tables = "g_user";
	private static String packageName = "com.xu.tt";
	private static String url = "localhost:3306/test";
	private static String userName = "root";
	private static String password = "shijie";

	private static String author = "XuDong";

	public static void main(String[] args) {
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();
		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		String projectPath = System.getProperty("user.dir");
		gc.setOutputDir(projectPath + "/src/main/java");
		gc.setAuthor(author);
		gc.setOpen(false);
		gc.setFileOverride(true);
		mpg.setGlobalConfig(gc);
		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setUrl("jdbc:mysql://" + url
				+ "?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
		dsc.setDriverName("com.mysql.cj.jdbc.Driver");
		dsc.setUsername(userName);
		dsc.setPassword(password);
		mpg.setDataSource(dsc);
		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent(packageName);
		mpg.setPackageInfo(pc);
		// 自定义配置
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
		};
		// 如果模板引擎是 freemarker
		String templatePath = "/templates/mapper.xml.ftl";
		// 自定义输出配置
		List<FileOutConfig> focList = new ArrayList<>();
		// 自定义配置会被优先输出
//		focList.add(new FileOutConfig(templatePath) {
//			@Override
//			public String outputFile(TableInfo tableInfo) {
//				// 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//				return projectPath + "/src/main/resources/mapper/"
////						+ pc.getModuleName() + "/"
//						+ tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//			}
//		});
		cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);
		// 配置模板
		TemplateConfig templateConfig = new TemplateConfig();
		templateConfig.setController(null);
		templateConfig.setService(null);
		templateConfig.setServiceImpl(null);
		templateConfig.setXml(null);
		mpg.setTemplate(templateConfig);
		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setEntityLombokModel(true);
		strategy.setRestControllerStyle(true);
		strategy.setInclude(tables);
		strategy.setControllerMappingHyphenStyle(true);
		mpg.setStrategy(strategy);
		mpg.setTemplateEngine(new FreemarkerTemplateEngine());
		mpg.execute();
	}

}
