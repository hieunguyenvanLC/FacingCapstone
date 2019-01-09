package Service;

import Connections.MyConnections;
import Model.SinhVien;
import org.springframework.stereotype.Controller;

import javax.xml.transform.Result;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class LoginService implements Serializable {
    List<SinhVien> sinhVienList = new ArrayList<SinhVien>();

    public List<SinhVien> getAllSinhViensList()  throws SQLException{
        return sinhVienList;
        MyConnections init = new MyConnections();
        try {
            init.init();
            Statement stmt=null;
            stmt = init.getConn().createStatement();
            String sql="select sinhvien.employee.* from employee";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()){
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            init.getConn().close();
        }
        return sinhVienList;
    }
}
