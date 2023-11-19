$(document).ready(function(){
    $("#userForm").validate({

        rules: {
            name: {
                required: true
            },
            email: {
                required: true,
                email: true
            },
            username: {
                required: true
            },
            mobileNo: {
                required: true,
                digits: true,
                minlength: 9
            },
            password: {
                required: true,
                minlength: 8
            }
        },
        messages: {
            name: "El campo nombre no puede estar vacío.",
            email: {
                required: "El campo correo no puede estar vacío.",
                email: "Ingresa una dirección de correo electrónico válida."
            },
            username: "El campo usuario no puede estar vacío.",
            mobileNo: {
                required: "El campo número no puede estar vacío.",
                digits: "Ingresa solo dígitos.",
                minlength: "El campo número debe contener al menos 9 dígitos."
            },
            password: {
                required: "El campo contraseña no puede estar vacío.",
                minlength: "La contraseña debe tener al menos 8 caracteres."
            }
        }

    })
})