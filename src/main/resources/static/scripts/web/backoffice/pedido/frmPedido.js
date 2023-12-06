$(document).ready(() => {
    var carritoPedido = []

    function listaProductos() {
        $.ajax({
            url: "/pedidos/listarProductos",
            type: "Get",
            dataType: "json",
            success: function (resultado) {
                let data = '';
                resultado.forEach(element => {
                    data += `
                    <div class="col-md-4">
                        <div class="card m-1 p-1">
                              <div class="row" productoId=${element.id}>
                                        <div class="col-md-7">
                                            <img class="float-left ml-1" src="/uploads/${element.foto}"
                                                alt="${element.foto}" style="max-width: 100%; max-height: 100%;" />
                                        </div>
                                        <div class="col-md-5 mt-4">
                                            <h2>${element.nombre}</h2>
                                            <p>${element.marca.nombre}</p>
                                            <button class="btn btn-primary" id="btn-agregar">Agregar</button>
                                        </div>
                              </div>
                        </div>
                    </div>
                    `;
                });
                $('#productosContenedor').html(data);
            },
            error: function () {
                console.error('Error al cargar datos de productos en pedido.');
            }
        })
    }

    function agregarProducto() {
        $(document).on("click", "#btn-agregar", function () {
            let btnAgregarProducto = $(this)[0].parentElement.parentElement;
            capturarValores(btnAgregarProducto);
        })
    }


    function capturarValores(valores) {

        const producto = {
            id: $(valores).attr("productoId"),
            nombreProducto: $(valores).find("h2").text(),
            marca: $(valores).find("p").text(),
            img: $(valores).find("img").attr("src"),
            cantidad: 1,
        }
        let verificarExistencia = carritoPedido.find(item => item.id === producto.id);
        if (verificarExistencia) {
            verificarExistencia.cantidad++;

        } else {
            carritoPedido.push(producto);
        }
        pintarCarritoPedido()
    }


    function pintarCarritoPedido() {
        let data = '';

        $.each(carritoPedido, function (index, producto) {
            data += `
            <tr>
                <td>${producto.nombreProducto}</td>
                <td>${producto.marca}</td>
                <td>${producto.cantidad}</td>
                <td>
                    <button class="btn btn-warning "id="agregarCantidad" productoId=${producto.id}>agregar</button>
                </td>
                <td>
                    <button class="btn btn-success "id="disminuirCantidad" productoId=${producto.id}>disminuir</button>
                </td>
            </tr>`;
        });

        $("#tbody").html(data);
        pintarFooter();
    }

    function pintarFooter() {
        let data = '';
        $("#footer").empty();
        $("#bodyFooter").empty();
        if (carritoPedido.length === 0) {
            data += `<th scope="row" colpsan="2">empieza a pedir</th>`
            $("#footer").html(data);

        } else {
            let cantidadTotal = 0;
            let dataTotal = '';
            $.each(carritoPedido, function (index, producto) {
                cantidadTotal += producto.cantidad;

            });
            dataTotal += `<tr><td>Totalidad:</td><td>${cantidadTotal}</td><td><button class="btn btn-primary" id="btn-vaciarCarritoPedido">Vaciar</button></td><td><button class="btn btn-primary" id="btn-guardarCarritoPedido">Generar Pedido</button></td></tr>`;
            $("#bodyFooter").html(dataTotal);
        }

    }

    function vaciarCarritoPedido() {
        $(document).on("click", "#btn-vaciarCarritoPedido", function () {
            carritoPedido = [];
            pintarCarritoPedido();

        })
    }

    function agregarCantidad() {
        $(document).on("click", "#agregarCantidad", function () {
            let btnAgregarCantidad = $(this);
            let id = $(btnAgregarCantidad).attr("productoId");
            let productoEnCarrito = carritoPedido.find(item => item.id === id);
            if (productoEnCarrito) {
                productoEnCarrito.cantidad++;
                pintarCarritoPedido();

            }

        })
    }

    function desminuirCantidad() {
        $(document).on("click", "#disminuirCantidad", function () {
            let btnDisminuirCantidad = $(this);
            let id = $(btnDisminuirCantidad).attr("productoId");
            let productoEnCarrito = carritoPedido.find(item => item.id === id);
            if (productoEnCarrito) {
                productoEnCarrito.cantidad--;
                if (productoEnCarrito.cantidad === 0) {
                    carritoPedido = carritoPedido.filter(producto => producto.id !== id);
                }
                pintarCarritoPedido();

            }


        })

    }

    //guardar pedido mediante el carritoPedido
    function guardarPedido() {
        $(document).on("click", "#btn-guardarCarritoPedido", function () {
            $.ajax({
                type: "POST",
                url: "/pedidos/guardarPedido",
                contentType: "application/json",
                data: JSON.stringify(carritoPedido),
                success: function (resultado) {
                    alert(resultado.mensaje);
                    carritoPedido = [];
                    pintarCarritoPedido();
                },
                error: function () {
                    alert('Error al guardar Pedido');
                }

            })
        })

    }

    listaProductos();
    agregarProducto();
    vaciarCarritoPedido();
    agregarCantidad();
    desminuirCantidad();
    guardarPedido();


})