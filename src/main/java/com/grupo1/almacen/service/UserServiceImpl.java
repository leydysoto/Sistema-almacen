package com.grupo1.almacen.service;

import com.grupo1.almacen.entity.Cargo;
import com.grupo1.almacen.entity.Empleado;
import com.grupo1.almacen.entity.Role;
import com.grupo1.almacen.entity.User;
import com.grupo1.almacen.entity.dto.request.UsuarioRequest;
import com.grupo1.almacen.repository.EmpleadoRepository;
import com.grupo1.almacen.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User guardarUsuario(UsuarioRequest usuarioRequest) {
        //Empleado-User-Role-Area-cargo
        // usuario
        User user = new User();
        Empleado empleado = new Empleado();
        if(usuarioRequest.getEmpleadoid()!=null){
            Empleado empleadoEncontrado=empleadoRepository.findById(usuarioRequest.getEmpleadoid()).get();

            if(empleadoEncontrado!=null){
                user.setId(empleadoEncontrado.getUser().getId());
                user.setUsername(empleadoEncontrado.getUser().getUsername());
                user.setPassword(empleadoEncontrado.getUser().getPassword());

                empleado.setId(empleadoEncontrado.getId());

            }

        }else{
            user.setUsername(usuarioRequest.getUsername());

            String password = usuarioRequest.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);

        }
        user.setNombres(usuarioRequest.getNombres());
        user.setEmail(usuarioRequest.getEmail());
        user.setTelefono(usuarioRequest.getTelefono());


        Role role = new Role();
        role.setId(usuarioRequest.getRoleid());
        user.setRole(role);

        User newUser = userRepository.save(user);

        // empleado
        empleado.setNombres(usuarioRequest.getNombres());
        empleado.setDni(usuarioRequest.getDni());
        empleado.setDireccion(usuarioRequest.getDireccion());

        empleado.setEmail(usuarioRequest.getEmail());
        empleado.setTelefono(usuarioRequest.getTelefono());

        Cargo cargo = new Cargo();
        cargo.setId(usuarioRequest.getCargoid());
        empleado.setCargo(cargo);

        //  relación entre el empleado y el usuario
        empleado.setUser(newUser);
        empleadoRepository.save(empleado);
        return newUser;
    }

    @Override
    public User guardarCredenciales(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public void removeSessionMessage() {
        HttpSession session=((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        session.removeAttribute("msg");
    }
}
