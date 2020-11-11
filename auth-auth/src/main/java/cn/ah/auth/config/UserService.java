package cn.ah.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private List<User> userList;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData(){
        String password = passwordEncoder.encode("123456");
        userList = new ArrayList<>();
        userList.add(new User("ah", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        userList.add(new User("hisaniwo", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
        userList.add(new User("hisaniwo", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<User> users = userList.stream()
                .filter(user -> user.getUsername().equals(s))
                .collect(Collectors.toList());
        if (!users.isEmpty()) {
            return users.get(0);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
