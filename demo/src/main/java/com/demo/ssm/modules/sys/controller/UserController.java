package com.demo.ssm.modules.sys.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.ssm.common.controller.BaseController;
import com.demo.ssm.modules.sys.entity.User;
import com.demo.ssm.modules.sys.security.interceptor.FormToken;
import com.demo.ssm.modules.sys.service.UserService;

/**
*
* @author zhoubin
*
* @date 2017年6月30日 下午4:04:05
*/
@Controller
@RequestMapping("${adminPath}/sys/user/")
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;

	/**
	 * 增加用户
	 * @param user
	 * @return
	 */
	@RequiresPermissions("add")
	@FormToken(validateToken=true)
	@RequestMapping("save")
	public String save(User user) {

		System.out.println("save user");
		
//		SystemService.entryptPassword(user.getPassword());
		System.out.println("增加成功");
		
		return "redirect:" + adminPath + "/sys/user/findAllUser";
	}
	
	/**
	 * 跳转到增加用户页面
	 * @date 2017年9月19日 下午2:20:59
	 */
	@RequestMapping("gotoUserForm")
	@FormToken(createToken=true)
	public String gotoUserForm() {

		System.out.println("gotoUserForm");
		
		return "ssm/modules/sys/userForm";
	}
	
	/**
	 * 查询所有用户
	 * @param model
	 * @date 2017年9月18日 下午5:20:40
	 */
	@RequestMapping("findAllUser")
	public String findAllUser(Model model) {
		List<User> userList = userService.findAllUser();
		model.addAttribute("userList", userList);
		return "ssm/modules/sys/userList";
	}
	
}
