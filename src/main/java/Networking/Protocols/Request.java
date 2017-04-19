package Networking.Protocols;

import java.io.Serializable;

/**
 * Created by Alexandra Muresan on 4/2/2017.
 */
public class Request implements Serializable {

    private RequestType type;


    private Object data;


    private Request(){}


    public RequestType getType(){
        return type;
    }

    private void setType(RequestType type){
        this.type = type;
    }

    public Object getData(){
        return data;
    }

    public void setData(Object data){
        this.data = data;
    }



    @Override
    public String toString(){
        return "Request{ type = " + getType() + " data = " + getData() + " } ";
    }

    public static class Builder{
        private Request request = new Request();

        public Builder type(RequestType type){
            request.setType(type);
            return this;
        }

        public Builder data(Object data){
            request.setData(data);
            return this;
        }

        public Request build(){
            return request;
        }
    }


}
