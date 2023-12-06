$(document).ready(()=>{
    function listaMedidas(){
        $.ajax({
            url:"/medidas/listarMedidas",
            type:"Get",
            dataType:"json",
            success:function (resultado){
                let data='';
                resultado.forEach(element=>{
                    data+=`
                <tr medidaId=${element.id}>
                    <td>${element.id}</td>
                    <td>${element.nombre}</td>
         
                    <td>
                    <button id="btn-delete" class="btn btn-warning">eliminar</button>
                    </td>
                        
                </tr>`
                });
                $('#tbody').html(data);
            },
            error: function() {
                console.error('Error al cargar datos de medidas.');
            }
        })

    }
    function  guardarMedida(){
        $(document).on("click","#btnguardar",function(){
            $.ajax({
                url: "/medidas/guardar",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({
                    nombre:$("#txtnombre").val()
                }),
                success:function(resultado){
                  /*  $('#respuesta').html('medida creada correctamente').css('display','block')*/
                    listaMedidas();
                    limpiar();
                    console.log("medida registrada")

                }

            })

        })

    }

    function eliminarMedida (){
        $(document).on("click","#btn-delete",function (){
            if(confirm("esta seguro de eliminar?")){
                let btnEliminar=$(this)[0].parentElement.parentElement;
                let id=$(btnEliminar).attr("medidaId");
                $.ajax({
                    url:"/medidas/eliminarMedidas/"+ id,
                    type:"DELETE",
                    dataType:"json",
                    success:function(resultado){
                        let mensajeHtml = `<span>${resultado.mensaje}</span><button id="btn-close">x</button>`;
                        $("#messages").html(mensajeHtml).css("display","block");
                        $("#btn-close").css({
                            background: "#FFC300",
                            color: "#fff",
                            border: "none",
                            cursor: "pointer",
                            padding: "5px 10px",
                            "margin-left": "5px"

                        });
                        listaMedidas();
                    }
                })
            }
        })
    }
    function ocultarMensaje(){
        $(document).on("click","#btn-close",function (){
            $(this).parent().css("display","none");
        })
    }


    function limpiar(){
        $('#txtnombre').val('');
    }

    listaMedidas();
    guardarMedida();
    eliminarMedida ();
    ocultarMensaje();


})



