package com.wiwi.jsoil.codeGen.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiwi.jsoil.codeGen.Column;
import com.wiwi.jsoil.codeGen.Function;

public class AddViewFileGenerator extends PageFileGenerator {
	private static final Logger logger = LoggerFactory.getLogger(AddViewFileGenerator.class);
	
	public AddViewFileGenerator( Function function,String projectRealPath, String viewPageSrcPath) {
		super();
		this.viewPageSrcPath = viewPageSrcPath;
		this.projectRealPath = projectRealPath;
		this.function = function;
		this.type = "Add";
	}

	/**
	 * 生成模板的List页面
	 * @param function
	 * @param srcPath
	 * @return
	 */
	@Override
	protected StringBuffer processTemplateContent(StringBuffer templateContent) {
		String content = templateContent.toString();
		
		//模块标题
		content = content.replaceAll("#function_title#", function.getName());
		content = content.replaceAll("#basePath#", function.getRequestBasePath() );
		//content.replaceAll("#query_group#", getQueryGroup(function));因返回内容中含有{}符号，导致replaceAll执行失败
		String[] splitContent = content.split("#field_input_group#");
		if(splitContent.length >1){
			content = splitContent[0] + getFieldInputGroup(function) + splitContent[1];
		}
		
		splitContent = content.split("#input_validator#");
		if(splitContent.length >1){
			content = splitContent[0] + getInputValidator(function) + splitContent[1];
		}
		
		return new StringBuffer(content);
	}
	
	
	/**
	 * <div class="form-group">
			<label class="col-sm-3 control-label no-padding-right"> 模块名称 </label>
			<div class="col-sm-9">
				<input type="text" th:field="${instance.name}" placeholder="模块名称" class="col-xs-10 col-sm-5" />
			</div>
		</div>
	 * @param function
	 * @return
	 */
	private static String getFieldInputGroup(Function function){
		StringBuffer content = new StringBuffer();
		
		for(Column column : function.getColumnList()){
			if(column.getId().equals("id"))continue;
			String notNullTip = "";
			if(column.isNotNull()){
				notNullTip = " * ";
			}
			content.append("							<div class='form-group'>\n");
			content.append("								<label class='col-sm-3 control-label no-padding-right'> "+ column.getName() + notNullTip + " </label>\n");
			content.append("								<div class='col-sm-9'>\n");
			content.append("									<input type='text' th:field='${instance." + column.getId() +"}' placeholder='"+ column.getName() +"' class='col-xs-10 col-sm-5' />\n");
			content.append("								</div>\n");
			content.append("							</div>\n");
		}
		return content.toString();
	}
	
	
	/**
	 *  name: "required",
		parentId: "required",
	 * @param function
	 * @return
	 */
	private static String getInputValidator(Function function){
		StringBuffer content = new StringBuffer();
		
		for(Column column : function.getColumnList()){
			if(column.getId().equals("id"))continue;
			if(column.isNotNull()){
				content.append("					" + column.getId() + ": \"required\",\n");
			}
		}
		String contentStr = content.toString();
		if(contentStr.endsWith(",")){
			contentStr = contentStr.substring(0,contentStr.length()-1);
		}
		logger.debug("添加页面的脚本验证：{}",contentStr);
		return contentStr;
	}
	
}
