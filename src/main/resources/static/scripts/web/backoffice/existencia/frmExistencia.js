$(document).on("click","#btnagregar",function(){
    limpiar();
    listarCombos();
    $("#modalNuevo").modal("show");


})
function limpiar(){
    $('#cbonombreproducto').empty();
    $('#cbocategoria').empty();
    $('#cbomarca').empty();
    $('#txtcantidad').val('');
}
function listarCombos(){
    $.ajax({
        type:"GET",
        url:"/existencias/categoria",
        dataType:"json",
        success:function (resultado) {
            $.each(resultado, function (index, value) {
                $("#cbocategoria").append(
                    `<option value="${value.nombre}">${value.nombre} </option>`
                )
            })
            $.ajax({
                type: "GET",
                url: "/existencias/marca",
                dataType: "json",
                success: function(resultado){
                    $.each(resultado, function(index, value){
                        $("#cbomarca").append(
                            `<option value="${value.nombre}">${value.nombre}</option>`
                        )
                    })
                    $.ajax({
                        type: "GET",
                        url: "/existencias/nombresproducto",
                        dataType: "json",
                        success: function(resultado){
                            $.each(resultado, function(index, value){
                                $("#cbonombreproducto").append(
                                    `<option value="${value.nombre}">${value.nombre}</option>`
                                )
                            })
                            $.ajax({
                                type: "GET",
                                url: "/existencias/medida",
                                dataType: "json",
                                success: function(resultado){
                                    $.each(resultado, function(index, value){
                                        $("#cbomedida").append(
                                            `<option value="${value.nombre}">${value.nombre}</option>`
                                        )
                                    })
                                }
                            })
                        }
                    })

                }
            })
        }

    })

    $(document).on("click", "#btnguardar", function(){
        $.ajax({
            type: "POST",
            url: "/existencias/guardar",
            contentType: "application/json",
            data: JSON.stringify({

                nombre: $("#cbonombreproducto").val(),
                categoria: $("#cbocategoria").val(),
                marca: $("#cbomarca").val(),
                cantidad: $("#txtcantidad").val(),
            }),
            success: function(resultado){
                alert(resultado.mensaje);
                listarExistencias();
                $("#modalNuevo").modal("hide");
            },
            error:function (xhr,status,error){
                if(xhr.response.JSON && xhr.responseJSON.mensaje){
                    alert("error:"+xhr.responseJSON.mensaje)

                }else{
                    alert("error al procesar solicitud")
                }
            }
        })
    });

    $(document).on("click",".btnaumentar",function (){
        $("#txtcantidadMovimiento").val("");
        $("#hddmovimiento").val($(this).attr("data-movimiento"));
        $("#hddCodigoExistencia").val($(this).attr("data-exiscod"));
        $("#modalNuevoMovimiento").modal("show");
    })




    $(document).on("click",".btndisminuir",function (){
        $("#txtcantidadMovimiento").val("");
        $("#hddmovimiento").val($(this).attr("data-movimiento"));
        $("#hddCodigoExistencia").val($(this).attr("data-exiscod"));
        $("#hddPedidoPendiente").val(pedidoIdSeleccionado);
        $("#modalNuevoMovimiento").modal("show");

    })



   $(document).on("click","#btnGuardarIncrementarDisminuir",function (){
        $.ajax({
            type: "POST",
            url: "/existencias/aumentardisminuir",
            contentType: "application/json",
            data: JSON.stringify({
                existenciaid: $("#hddCodigoExistencia").val(),
                cantidad: $("#txtcantidadMovimiento").val(),
                movimiento: $("#hddmovimiento").val(),
                pedido:$("#hddPedidoPendiente").val()


            }),
            success: function(resultado){
                alert(resultado.mensaje);
                listarExistencias();
                $("#modalNuevoMovimiento").modal("hide");

            },
            error:function (xhr,status,error){
                if(xhr.response.JSON && xhr.responseJSON.mensaje){
                    alert("error:"+xhr.responseJSON.mensaje)

                }else{
                    alert("error al procesar solicitud")
                }
            }
        })
    })

    function listarExistencias(){
        $.ajax({
            type: "GET",
            url: "/existencias/listarExistencias",
            dataType: "json",
            success: function(resultado){
                $("#tblexistencia > tbody").html("");
                $.each(resultado, function(index, value){
                    $("#tblexistencia > tbody").append("<tr>" +
                        "<td>"+value.id+"</td>"+
                        "<td>"+value.producto.id+"</td>"+
                        "<td>"+value.producto.nombre+"</td>"+
                        "<td>"+value.producto.categoria.nombre+"</td>"+
                        "<td>"+value.producto.marca.nombre+"</td>"+
                        "<td>"+value.producto.medida.nombre+"</td>"+
                        "<td>"+value.cantidad+"</td>"+
                        "<td>"+value.producto.tipoAlmacen.nombre+"</td>"+
                        "<td>"+
                        "<button type='button' class='btn btn-info btnaumentar'" +
                        " data-exiscod='"+value.id+"'"+
                        " data-movimiento='"+1+"'"+
                        ">Aumentar</button>"+
                        "<button type='button' class='btn btn-warning btndisminuir'" +
                        " data-exiscod='"+value.id+"'"+
                        " data-movimiento='"+-1+"'"+
                        ">Disminuir</button>"+
                        "</td></tr>"
                    );
                });
            }
        });
    }
    //cuando seleccione "HACIENDO" almacenará el id en la variable
    var pedidoIdSeleccionado;

    //cuando seleccione  HECHO Y PEDIDO CAMBIARÁ la variable

    $(document).on("change", "#estadoCombo", function () {
        const idPedido= $(this).find(":selected").data("pedido-id");
        const nuevoEstado = $(this).val();
        if($(this).val()=="HECHO"){
            pedidoIdSeleccionado = null;
        }
        else if($(this).val()=="PENDIENTE"){
            pedidoIdSeleccionado = null;
        }
        else if ($(this).val() == "HACIENDO") {
            pedidoIdSeleccionado = idPedido;
        } else {
            pedidoIdSeleccionado = null;
        }
        $.ajax({
            url: `/pedidos/${idPedido}/actualizarEstado?nuevoEstado=${nuevoEstado}`,
            type: 'PATCH',
            success: function (data) {
                //es un void
            },
            error: function (error) {
                console.error('Error:', error);
            }
        });
    });

}