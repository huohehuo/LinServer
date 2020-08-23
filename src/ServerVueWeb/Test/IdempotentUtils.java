package ServerVueWeb.Test;

import Utils.Lg;
import WebSide.UserDao;
import WebSide.WebResponse;
import com.google.gson.Gson;
import sun.misc.LRUCache;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static WebSide.Utils.HttpRequestUtils.ReadAsChars;

public class IdempotentUtils {
    private static LRUCache<String,Integer> reqCache = new LRUCache<String,Integer>(100){

        @Override
        protected Integer create(String s) {
            Lg.e("创建lru"+s);
            return 1;
        }

        @Override
        protected boolean hasName(Integer integer, String s) {

            return true;
        }
    };
}
