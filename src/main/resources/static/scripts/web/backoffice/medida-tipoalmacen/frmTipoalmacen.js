$(document).ready(()=>{
    function listaTipoAlmacen(){
        $.ajax({
            url:"/tipoalmacen/listarTipoalmacen",
            type:"Get",
            dataType:"json",
            success:function (resultado){
                let data='';
                resultado.forEach(element=>{
                    data+=`
                <tr tipoalmacenId=${element.id}>
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
                console.error('Error al cargar datos de sección del  almacén.');
            }
        })

    }
    function  guardarTipoAlmacen(){
        $(document).on("click","#btnguardar",function(){
            console.log("listo")
            $.ajax({
                url: "/tipoalmacen/guardar",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({
                    nombre:$("#txtnombre").val()

                }),

                success:function(resultado){
                  /*  $('#respuesta').html('medida creada correctamente').css('display','block')*/
                    listaTipoAlmacen();
                    limpiar();

                    console.log("sección del almacén registrada");

                }

            })

        })

    }

    function eliminarTipoAlmacen(){
        $(document).on("click","#btn-delete",function (){

            if(confirm("esta seguro de eliminar?")){
                let btnEliminar=$(this)[0].parentElement.parentElement;

                let id=$(btnEliminar).attr("tipoalmacenId");

                $.ajax({
                    url:"/tipoalmacen/eliminarTipoalmacen/"+ id,
                    type:"DELETE",
                    dataType:"json",
                    success:function(resultado){
                        console.log("aquii")
                        $("#messages").html(resultado.mensaje).css("display","block");
                        listaTipoAlmacen();
                    }
                })
            }
        })
    }

    function limpiar(){
        $('#txtnombre').val('');
    }

    listaTipoAlmacen();
    guardarTipoAlmacen();
    eliminarTipoAlmacen ()


})



