package com.wiwi.jsoil.sys.sms.util;

public class SMSUtil
{
//  public static PropertyValue SMS_TYPE_CHANGE_PASSWORD = new PropertyValue("修改密码", "changePassword");
//  public static PropertyValue SMS_TYPE_REGISTER = new PropertyValue("注册会员", "register");
//  public static final int OUT_OF_SERVICE_TIME = 5;
//
//  public static JSONResult sendChangePasswordMessageCode(String telphone)
//  {
//    return sendMessage(telphone, SMS_TYPE_CHANGE_PASSWORD.getValue().toString());
//  }
//
//  public static JSONResult sendRegisterCode(String telphone)
//  {
//    return sendMessage(telphone, SMS_TYPE_REGISTER.getValue().toString());
//  }
//
//  private static JSONResult sendMessage(String telphone, String type) {
//    if ((telphone == null) || ("".equals(telphone))) {
//      JSONResult sendResult = new JSONResult();
//      sendResult.setResult(false);
//      sendResult.setMsg("请输入正确的手机号！");
//      return sendResult;
//    }
//
//    int code = (int)(Math.random() * 900000.0D + 100000.0D);
//    String templetCode = getMessageTempletCode(type);
//    JSONResult sendResult = MessageSend.sendCode(telphone, templetCode, 
//      new String[] { code, "5" });
//    try
//    {
//      SmsRecord message = new SmsRecord();
//      message.setResult(sendResult.getJSONObject().toString() + sendResult.getMsg());
//      message.setSendDatas(code + "," + 5);
//      message.setMessageType(type);
//      Date now = new Date();
//      message.setSendTime(now);
//      message.setCancelTime(DateUtils.addMinute(now, 5));
//      message.setTelphone(telphone);
//      new SmsRecordService().insert(message);
//
//      if (sendResult.isResult()) {
//        JSONResult result = new JSONResult();
//        result.setResult(true);
//        JSONObject resultJson = new JSONObject();
//        resultJson.put("code", code);
//        result.setResultJson(resultJson);
//        return result;
//      }
//    } catch (Exception e) {
//      sendResult.setResult(false);
//      sendResult.setMsg(e.getMessage());
//      e.printStackTrace();
//
//      return sendResult; }
//  }
//
//  private static String getMessageTempletCode(String type) {
//    String templetCode = "";
//    if (SMS_TYPE_REGISTER.getValue().equals(type))
//      return AppConstants.getProperty("SMS_TEMPLEATE_CODE_REGISTER");
//    if (SMS_TYPE_CHANGE_PASSWORD.getValue().equals(type)) {
//      return AppConstants.getProperty("SMS_TEMPLEATE_CODE_CHANGEPASSWORD");
//    }
//
//    return templetCode;
//  }
//
////  // ERROR //
////  public static JSONResult validateCode(String telphone, String code)
////  {
////    // Byte code:
////    //   0: new 64	com/wiwi/jsoil/util/JSONResult
////    //   3: dup
////    //   4: invokespecial 66	com/wiwi/jsoil/util/JSONResult:<init>	()V
////    //   7: astore_2
////    //   8: new 215	com/wiwi/jsoil/sys/sms/model/SmsRecordQ
////    //   11: dup
////    //   12: invokespecial 217	com/wiwi/jsoil/sys/sms/model/SmsRecordQ:<init>	()V
////    //   15: astore_3
////    //   16: aload_3
////    //   17: new 91	java/lang/StringBuilder
////    //   20: dup
////    //   21: ldc 218
////    //   23: invokespecial 97	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
////    //   26: aload_0
////    //   27: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
////    //   30: ldc 220
////    //   32: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
////    //   35: aload_1
////    //   36: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
////    //   39: ldc 222
////    //   41: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
////    //   44: invokevirtual 99	java/lang/StringBuilder:toString	()Ljava/lang/String;
////    //   47: invokevirtual 224	com/wiwi/jsoil/sys/sms/model/SmsRecordQ:setOtherCondition	(Ljava/lang/String;)V
////    //   50: aload_3
////    //   51: ldc 227
////    //   53: invokevirtual 229	com/wiwi/jsoil/sys/sms/model/SmsRecordQ:setOrderByProperty	(Ljava/lang/String;)V
////    //   56: aload_3
////    //   57: ldc 232
////    //   59: invokevirtual 234	com/wiwi/jsoil/sys/sms/model/SmsRecordQ:setOrderByKind	(Ljava/lang/String;)V
////    //   62: new 160	com/wiwi/jsoil/sys/sms/service/SmsRecordService
////    //   65: dup
////    //   66: invokespecial 162	com/wiwi/jsoil/sys/sms/service/SmsRecordService:<init>	()V
////    //   69: aload_3
////    //   70: invokevirtual 237	com/wiwi/jsoil/sys/sms/service/SmsRecordService:getList	(Lcom/wiwi/jsoil/db/PageUtil;)Ljava/util/List;
////    //   73: astore 4
////    //   75: aload 4
////    //   77: ifnull +94 -> 171
////    //   80: aload 4
////    //   82: invokeinterface 241 1 0
////    //   87: ifle +84 -> 171
////    //   90: aload 4
////    //   92: iconst_0
////    //   93: invokeinterface 247 2 0
////    //   98: checkcast 108	com/wiwi/jsoil/sys/sms/model/SmsRecord
////    //   101: astore 5
////    //   103: new 141	java/util/Date
////    //   106: dup
////    //   107: invokespecial 143	java/util/Date:<init>	()V
////    //   110: invokevirtual 251	java/util/Date:getTime	()J
////    //   113: aload 5
////    //   115: invokevirtual 255	com/wiwi/jsoil/sys/sms/model/SmsRecord:getCancelTime	()Ljava/util/Date;
////    //   118: invokevirtual 251	java/util/Date:getTime	()J
////    //   121: lsub
////    //   122: ldc2_w 259
////    //   125: lcmp
////    //   126: ifle +37 -> 163
////    //   129: aload_2
////    //   130: iconst_0
////    //   131: invokevirtual 67	com/wiwi/jsoil/util/JSONResult:setResult	(Z)V
////    //   134: new 116	org/json/JSONObject
////    //   137: dup
////    //   138: invokespecial 171	org/json/JSONObject:<init>	()V
////    //   141: astore 6
////    //   143: aload 6
////    //   145: ldc_w 261
////    //   148: ldc_w 263
////    //   151: invokevirtual 265	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
////    //   154: pop
////    //   155: aload_2
////    //   156: aload 6
////    //   158: invokevirtual 178	com/wiwi/jsoil/util/JSONResult:setResultJson	(Lorg/json/JSONObject;)V
////    //   161: aload_2
////    //   162: areturn
////    //   163: aload_2
////    //   164: iconst_1
////    //   165: invokevirtual 67	com/wiwi/jsoil/util/JSONResult:setResult	(Z)V
////    //   168: goto +45 -> 213
////    //   171: aload_2
////    //   172: iconst_0
////    //   173: invokevirtual 67	com/wiwi/jsoil/util/JSONResult:setResult	(Z)V
////    //   176: new 116	org/json/JSONObject
////    //   179: dup
////    //   180: invokespecial 171	org/json/JSONObject:<init>	()V
////    //   183: astore 5
////    //   185: aload 5
////    //   187: ldc_w 261
////    //   190: ldc_w 268
////    //   193: invokevirtual 265	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
////    //   196: pop
////    //   197: aload_2
////    //   198: aload 5
////    //   200: invokevirtual 178	com/wiwi/jsoil/util/JSONResult:setResultJson	(Lorg/json/JSONObject;)V
////    //   203: goto +10 -> 213
////    //   206: astore 4
////    //   208: aload 4
////    //   210: invokevirtual 187	java/lang/Exception:printStackTrace	()V
////    //   213: aload_2
////    //   214: areturn
////    //
////    // Exception table:
////    //   from	to	target	type
////    //   62	162	206	com/wiwi/jsoil/exception/RenderException
////    //   62	162	206	com/wiwi/jsoil/exception/DaoException
////    //   163	203	206	com/wiwi/jsoil/exception/RenderException
////    //   163	203	206	com/wiwi/jsoil/exception/DaoException
////  }
}