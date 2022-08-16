package com.atyyx.SpringMVC.Controller;

import com.atyyx.SpringMVC.Dao.EmployeeDao;
import com.atyyx.SpringMVC.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * 1.查询所有的员工信息  --->/employee --->get
 * 2.跳转到添加页面  ---->to/add  ---- >get
 * 3.新增员工信息   --->/employee---->post
 * 4.跳转到修改页面---->employee/1----->get
 * 5.修改员工信息----->employee--->put
 * 6.删除员工信息---->employee/1---->delete
 */
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @GetMapping("/employee")
    public String getAllEmployee(Model model)
    {
        // 获取所有的员工信息
        Collection<Employee> allEmployee = employeeDao.getAll();
        // 将所有的员工信息在请求域中共享
        model.addAttribute("allEmployee",allEmployee);
        return "employee_list";
    }

    @PostMapping("/employee")
    public String addEmployee(Employee employee)
    {

        // 保存员工信息
        employeeDao.save(employee);
        // 重新访问员工信息   --列表页面就是没有数据，必须得访问这个实现列表页面功能页面
        // 重定向到列表功能   --让浏览器重新去访问这个地址
        return "redirect:/employee";
    }

    @GetMapping("/employee/{id}")
    public String toUpdate(@PathVariable("id") Integer id,Model model)
    {
        // 根据id查询员工信息
        Employee employee = employeeDao.get(id);
        // 将员工信息共享到请求域中
        model.addAttribute("employee",employee);
        // 跳转到employee_update.html
        return "employee_update";
    }

    @PutMapping("/employee")
    public String updateEmployee(Employee employee)
    {
        // 修改员工信息
        employeeDao.save(employee);

        return "redirect:/employee";
    }

    @DeleteMapping("/employee/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id)
    {
        // 删除员工信息
        employeeDao.delete(id);
        //重定向到员工信息页面
        return "redirect:/employee";
    }
}
