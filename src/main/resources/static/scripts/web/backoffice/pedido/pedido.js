$(document).ready(() => {
    let listaPedidos
    function listarPedidos() {
        $.ajax({
            url: "/pedidos/listarPedidos",
            type: "GET",
            dataType: "json",
            success: function (resultado) {
                listaPedidos=resultado;
                let data = '';
                resultado.forEach(element => {

                    data += `<tr class="fila-pedido">
                      <td>${element.idPedido}</td>
                      <td>${element.numero}</td>
                      <td>${element.fechaCreacion}</td>
                      <td>${element.fechaRecibida}</td>
                      <td>${element.estado}</td>
                      <td>${element.usuario}</td>
                      <td>
                          <a href="#" class="btn btn-primary  btn-detalles"   data-id="${element.idPedido}"style="background-color: #bf790a; border: none; outline: none;" >Detalles</a>
                      </td>
                  </tr>`;
                });
                $('#body').html(data);
            },
            error: function (xhr, status, error) {
                console.error('Error al cargar datos de pedidos.');
                console.log('XHR:', xhr);
                console.log('Status:', status);
                console.log('Error:', error);
            }
        });
    }

    $(document).on("click", ".btn-detalles", function () {
        let idPedido = $(this).data("id");
        let pedido = encontrarPedidoPorId(idPedido);
        mostrarDetallesPedido(pedido);

    });
    //retorna el objeto pedido encontrado en la lista global "listaPedidos"
    function encontrarPedidoPorId(idPedido){
        return listaPedidos.find(p=>p.idPedido==idPedido);

    }
    function mostrarDetallesPedido(pedido) {
        let detallesHtml = `
            <div style="margin-bottom: 20px;">
                <h3>Datos del Pedido</h3>
                <p>ID: ${pedido.idPedido}</p>
                <p>Número: ${pedido.numero}</p>
                <p>Fecha Creación: ${pedido.fechaCreacion}</p>
                <p>Fecha Recibida: ${pedido.fechaRecibida}</p>
                <p>Estado: ${pedido.estado}</p>
                <p>Usuario: ${pedido.usuario}</p>
            </div>
            <div style="margin-bottom: 20px;">
                <h3>Productos del Pedido</h3>
                <ul>
            `;
            detallesHtml += '';
            pedido.detallePedidos.forEach(detalle => {
                detallesHtml += `<li data-id=${detalle.producto.id}>${detalle.producto.nombre} - ${detalle.cantidad}-${detalle.producto.medida.nombre}-${detalle.producto.marca.nombre}</li>`;
            });
            detallesHtml += `
                </ul>
            </div>
            <div style="margin-bottom: 120px;" >
                <h3>Estado del Pedido</h3>
                <select id="estadoPedido" class="form-control text-center">
                    <option value="PENDIENTE" ${pedido.estado.toLowerCase() === 'pendiente' ? 'selected' : ''}>PENDIENTE</option>
                    <option value="HECHO" ${pedido.estado.toLowerCase() === 'hecho' ? 'selected' : ''}>HECHO</option>
                    <option value="HACIENDO" ${pedido.estado.toLowerCase() === 'haciendo' ? 'selected' : ''}>HACIENDO</option>
                </select>
         
            </div>
            <div style="margin-bottom: 20px;">
                <a href="/existencias/listar?idPedido=${pedido.idPedido}" class="btn btn-primary">EXISTENCIA</a>
            </div>
        `;

        $('.ladoderecho').html(detallesHtml);
    }
    //ESTO NO ESTÁ FUNCIONANDO
    function cambiarDeEstado(){
        $('#estadoPedido').on('change', function() {
            const nuevoEstado = $(this).val();
            // Puedes hacer algo con el nuevo estado, como enviarlo al servidor mediante una solicitud AJAX
            console.log('Nuevo Estado del Pedido:', nuevoEstado);
        });

    }

    listarPedidos();
    cambiarDeEstado();
});
