package com.johnny.web;

import com.google.gson.Gson;
import com.johnny.pojo.User;
import com.johnny.service.UserService;
import com.johnny.service.impl.UserServiceImpl;
import com.johnny.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();


    protected void ajaxExistsUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 獲取請求的參數username
        String username = req.getParameter("username");
        // 調用userService.existsUsername();
        boolean existsUsername = userService.existsUsername(username);
        // 把返回的結果封裝成為map物件
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("existsUsername",existsUsername);

        Gson gson = new Gson();
        String json = gson.toJson(resultMap);

        resp.getWriter().write(json);
    }

    /**
     * 登出
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1、銷毀Session中用戶登入的訊息（或者銷毀Session）
        req.getSession().invalidate();
//        2、重定向到首頁（或登入頁面）。
        resp.sendRedirect(req.getContextPath());
    }
    /**
     * 處理登入的功能
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //  1、獲取請求的參數
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        // 調用 userService.login()登入處理業務
        User loginUser = userService.login(new User(null, username, password, null));
        // 如果等於null,說明登入失敗!
        if (loginUser == null) {
            // 把錯誤訊息，和回顯的表單項訊息，保存到Request域中
            req.setAttribute("msg", "帳號或密碼錯誤!");
            req.setAttribute("username", username);
            //   跳回登入頁面
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        } else {
            // 登入成功
            // 保存用戶登入的訊息到Session域中
            req.getSession().setAttribute("user", loginUser);
            //跳到成功頁面login_success.html
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
        }

    }

    /**
     * 處理註冊的功能
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 獲取Session中的驗證碼
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // 删除Session中的驗證碼
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        //  1、獲取請求的參数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        User user = WebUtils.copyParamToBean(req.getParameterMap(), new User());

//        2、檢查驗證碼是否正確
        if (token!=null && token.equalsIgnoreCase(code)) {
//        3、檢查用戶名是否可用
            if (userService.existsUsername(username)) {
                System.out.println("帳號名稱[" + username + "]已存在");

                // 把回顯訊息，保存到Request域中
                req.setAttribute("msg", "帳號名稱已存在!!");
                req.setAttribute("username", username);
                req.setAttribute("email", email);

//        跳回註冊頁面
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
            } else {
                //      可用
//                調用Service保存到資料庫
                userService.registUser(new User(null, username, password, email));
//
//        跳到註冊成功頁面 regist_success.jsp
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
            }
        } else {
            // 把回顯訊息，保存到Request域中
            req.setAttribute("msg", "驗證碼錯誤!!");
            req.setAttribute("username", username);
            req.setAttribute("email", email);

            System.out.println("驗證碼[" + code + "]錯誤");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
        }

    }


}
