package capstone.fps.common;

import javax.servlet.ServletContext;

public final class Methods {

    private ServletContext context;

    public Methods() {
    }

    public Methods(ServletContext context) {
        this.context = context;
    }

    public java.sql.Date getSqlNow(){
        return new java.sql.Date(new java.util.Date().getTime());
    }

    public String getAbsolutePath(){
        //        this.absolutePath = context.getRealPath("resources/upload");
        return context.getContextPath();
    }
}
