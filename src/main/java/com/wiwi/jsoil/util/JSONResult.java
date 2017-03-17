package com.wiwi.jsoil.util;

import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONResult
  implements Serializable
{
  private static final long serialVersionUID = -856871704L;
  private boolean result;
  private String msg;
  private Object resultJson;

  public boolean isResult()
  {
    return this.result;
  }

  public void setResult(boolean result) {
    this.result = result;
  }

  public String getMsg() {
    return this.msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Object getResultJson() {
    return this.resultJson;
  }

  public void setResultJson(String obj) {
    if (obj != null)
      this.resultJson = obj;
  }

  public void setResultJson(JSONObject obj) {
    if (obj != null)
      this.resultJson = obj;
  }

  public void setResultJson(JSONArray arry) {
    if (arry != null)
      this.resultJson = arry;
  }

  public JSONObject getJSONObject() {
    JSONObject obj = new JSONObject();
    obj.put("success", this.result);
    if (this.resultJson instanceof JSONArray) {
      if (this.resultJson == null)
        this.resultJson = new JSONArray();
      obj.put("result", (JSONArray)this.resultJson);
    } else if (this.resultJson instanceof JSONObject) {
      if (this.resultJson == null)
        this.resultJson = new JSONObject();
      obj.put("result", (JSONObject)this.resultJson);
    }
    else obj.put("result", this.resultJson);

    return obj;
  }
}