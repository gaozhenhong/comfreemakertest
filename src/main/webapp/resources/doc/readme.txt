1、命名规则：
	1）、List表头的字段是排序字段，需要与SQL里的字段表名一致。如针对user对象的属性type类型名称排序，则这里应该是:typeName。且在字段获取展现时用：type.name。
	2）、常规页面，页头采用的是多标签页，主要有：列表、添加两个如果不需要添加，则只需要个性list页面中的：<ul th:include="fragment :: topTab(为：
	<ul th:include="fragment :: noAddTopTab( 即可。
	3）、要引用其他模块的查看页时，使用模式对话框查看，并传入参数：isModal=true。
	
	
2、编写示例：thymeleaf的模板引用 ::前面的就是放置模板的文件名。

3、MAVEN的Responsity：http://mvnrepository.com/,任何需要的jar包都可以从该网站查询。

4、在HTML页面中需要引入脚本时，使用如下方法：
	<script th:inline="javascript">
		/*<![CDATA[*/
		/*]]>*/
	</script>
	
	
4、themyleaf判断不等于Null：<td th:text="${obj.logoutTime}?${#dates.format(obj.logoutTime, 'yyyy-MM-dd HH:mm')}:''">字段内容</td>

5、在Eclipse工程目录下.settings/org.eclipse.wst.common.component文件中配置发布目录，这样src/project/webapp下的文件才能发布到tomcat的目录中。

6、th:utext="${html}"，HTML内容原样输出。

7、 th:with自定义变量：th:with="fisrtPerson=${persons[0]}"
<div th:with="firstPer=${persons[0]},secondPer=${persons[1]}"> 