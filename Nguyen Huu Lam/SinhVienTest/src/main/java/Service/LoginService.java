package Service;

import Model.SinhVien;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginService {
    List<SinhVien> sinhVienList=new ArrayList<SinhVien>();

    public List<SinhVien> getAllSinhViensList(){
        return sinhVienList;
    }
}
