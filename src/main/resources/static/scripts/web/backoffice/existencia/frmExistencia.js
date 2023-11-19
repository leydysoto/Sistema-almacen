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
        $("#modalNuevoMovimiento").modal("show");

    })
   $(document).on("click","#btnGuardarIncrementarDisminuir",function (){
       console.log("antes de la solicitud");
        $.ajax({
            type: "POST",
            url: "/existencias/aumentardisminuir",
            contentType: "application/json",
            data: JSON.stringify({
                existenciaid: $("#hddCodigoExistencia").val(),
                cantidad: $("#txtcantidadMovimiento").val(),
                movimiento: $("#hddmovimiento").val(),

            }),
            success: function(resultado){
                alert(resultado.mensaje);
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


}