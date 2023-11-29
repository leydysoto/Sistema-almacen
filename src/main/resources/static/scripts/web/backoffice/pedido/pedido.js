$(document).ready(() => {
    function listarPedidos() {
        $.ajax({
            url: "/pedidos/listarPedidos",
            type: "GET",
            dataType: "json",
            success: function (resultado) {
                let data = '';
                resultado.forEach(element => {
                    data += `<tr>
                      <td>${element.idPedido}</td>
                      <td>${element.numero}</td>
                      <td>${element.fechaCreacion}</td>
                      <td>${element.fechaRecibida}</td>
                      <td>${element.estado}</td>
                      <td>${element.usuario}</td>
                      <td>
                          <a href="#" class="btn btn-primary" style="background-color: #bf790a; border: none; outline: none;">Detalles</a>
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

    // Llama a la función listarPedidos cuando se carga la página
    listarPedidos();
});
