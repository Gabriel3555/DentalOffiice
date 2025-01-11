<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Dashboard - SB Admin</title>

    <!-- DataTables CSS -->
    <link href="https://cdn.datatables.net/1.13.7/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/responsive/2.5.0/css/responsive.bootstrap5.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/buttons/2.4.2/css/buttons.bootstrap5.min.css" rel="stylesheet">

    <!-- Agregar en el head -->
    <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.10.2/dist/sweetalert2.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.10.2/dist/sweetalert2.all.min.js"></script>
    <!-- Custom CSS -->
    <link href="css/styles.css" rel="stylesheet" />

    <!-- FontAwesome -->
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
<%
    Long id = (Long) session.getAttribute("id");
    String Name = (String) session.getAttribute("name");
    String Lastname = (String) session.getAttribute("last_name");
    String email = (String) session.getAttribute("email");
    String phoneNumber = (String) session.getAttribute("phone_number");
    String address = (String) session.getAttribute("address");
    String birthDate = (String) session.getAttribute("birth_date");
    Long scheduleId = (Long) session.getAttribute("schedule_id");
    Long userId = (Long) session.getAttribute("user_id");
%>
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <!-- Navbar Brand-->
    <a class="navbar-brand ps-3" href="doctor.jsp"><i class="fa-solid fa-tooth"></i> Consultorio</a>
    <!-- Sidebar Toggle-->
    <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
    <!-- Navbar-->
    <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" href="#!">Info</a></li>
                <li><a class="dropdown-item" href="logOut">Logout</a></li>
            </ul>
        </li>
    </ul>
</nav>
<div id="layoutSidenav">
    <div id="layoutSidenav_nav">
        <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
            <div class="sb-sidenav-menu">
                <div class="nav">
                    <a class="nav-link" href="doctor.jsp">
                        <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                        Información
                    </a>
                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                        <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                        Acciones
                        <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                    </a>
                    <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                        <nav class="sb-sidenav-menu-nested nav">
                            <a class="nav-link" href="#" id="verTurnos">Ver turnos</a>
                            <a class="nav-link" href="agregar">Agregar Turnos</a>
                        </nav>
                    </div>
                    <a class="nav-link" href="#">
                        <div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
                        Programación
                    </a>
                </div>
            </div>
            <div class="sb-sidenav-footer">
                <div class="small">Logged in as:</div>
                <%=Name + " " + Lastname%>
            </div>
        </nav>
    </div>
    <div id="layoutSidenav_content">
        <main>
            <div class="container-fluid px-4" id="info-container">
                <h1 class="mt-4" style="text-align: center">Notas Destacadas</h1>
                <div class="row d-flex justify-content-center flex-wrap">
                    <div class="card" style="width: 18rem; margin: 10px;">
                        <img class="card-img-top" src="assets/img/people-image.png" alt="Card image cap">
                        <div class="card-body">
                            <h3 class="card-title" style="text-align: center">Cantidad de pacientes</h3>
                        </div>
                    </div>
                    <div class="card" style="width: 18rem; margin: 10px;">
                        <img class="card-img-top" src="assets/img/dentist-image.png" alt="Card image cap">
                        <div class="card-body">
                            <p class="card-text">Cantidad de Odontologos</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container-fluid px-4" id="turns-container" style="display: none;">
                <h1 class="mt-4" style="text-align: center">Mis Turnos</h1>
                <div class="card mb-4">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <div>
                            <i class="fas fa-calendar-alt me-1"></i>
                            Turnos Programados
                        </div>
                        <button class="btn btn-secondary btn-sm" onclick="backToInfo()">
                            <i class="fas fa-arrow-left me-1"></i>Volver
                        </button>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table id="turnsTable" class="table table-striped table-hover">
                                <thead>
                                <tr>
                                    <th>Fecha</th>
                                    <th>Hora</th>
                                    <th>Paciente</th>
                                    <th>Acciones</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container-fluid px-4" id="create-turn-container" style="display: none;">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0"><i class="fas fa-calendar-plus me-2"></i>Crear Nuevo Turno</h5>
                    </div>
                    <div class="card-body">
                        <form id="createTurnForm" action="SvTurnos" method="POST">
                            <input type="hidden" name="action" value="create">

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="patientId" class="form-label">ID del Paciente</label>
                                    <input type="number" class="form-control" id="patientId" name="patientId" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="doctorId" class="form-label">ID del Doctor</label>
                                    <input type="number" class="form-control" id="doctorId" name="doctorId" required>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="date" class="form-label">Fecha</label>
                                    <input type="date" class="form-control" id="date" name="date" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="time" class="form-label">Hora</label>
                                    <input type="time" class="form-control" id="time" name="time" required>
                                </div>
                            </div>

                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <button type="button" class="btn btn-secondary me-md-2" onclick="backToInfo()">
                                    <i class="fas fa-arrow-left me-2"></i>Cancelar
                                </button>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save me-2"></i>Guardar Turno
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </main>
        <footer class="py-4 bg-light mt-auto">
            <div class="container-fluid px-4">
                <div class="d-flex align-items-center justify-content-between small">
                    <div class="text-muted">Copyright &copy; Mi consultorio 2025</div>
                    <div>
                        <a href="#">Privacy Policy</a>
                        &middot;
                        <a href="#">Terms &amp; Conditions</a>
                    </div>
                </div>
            </div>
        </footer>
    </div>
</div>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.7.0.js"></script>

<!-- DataTables JS -->
<script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.7/js/dataTables.bootstrap5.min.js"></script>
<script src="https://cdn.datatables.net/responsive/2.5.0/js/dataTables.responsive.min.js"></script>
<script src="https://cdn.datatables.net/responsive/2.5.0/js/responsive.bootstrap5.min.js"></script>
<script src="https://cdn.datatables.net/buttons/2.4.2/js/dataTables.buttons.min.js"></script>
<script src="https://cdn.datatables.net/buttons/2.4.2/js/buttons.bootstrap5.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.10.1/jszip.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
<script src="https://cdn.datatables.net/buttons/2.4.2/js/buttons.html5.min.js"></script>
<script src="https://cdn.datatables.net/buttons/2.4.2/js/buttons.print.min.js"></script>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>

<!-- Custom JS -->
<script>

    document.querySelector('a[href="agregar"].nav-link').addEventListener('click', function(e) {
        e.preventDefault();
        document.getElementById('info-container').style.display = 'none';
        document.getElementById('turns-container').style.display = 'none';
        document.getElementById('create-turn-container').style.display = 'block';
    });

    let turnsTable;

    document.getElementById('verTurnos').addEventListener('click', function(e) {
        e.preventDefault();
        document.getElementById('info-container').style.display = 'none';
        document.getElementById('turns-container').style.display = 'block';

        if ($.fn.DataTable.isDataTable('#turnsTable')) {
            $('#turnsTable').DataTable().destroy();
        }

        turnsTable = $('#turnsTable').DataTable({
            ajax: {
                url: 'SvTurnos',
                dataSrc: function(json) {
                    console.log('Datos recibidos del servidor:', JSON.stringify(json, null, 2));
                    return json;
                }
            },
            columns: [
                {
                    data: 'app_date',
                    render: function(data, type, row) {
                        console.log('Fila completa en fecha:', row);
                        if (!data) return '';
                        return new Date(data).toLocaleDateString('es-ES');
                    }
                },
                {
                    data: 'app_time',
                    render: function(data, type, row) {
                        if (!data) return '';
                        return data.substring(0, 5);
                    }
                },
                {
                    data: null,
                    render: function(data, type, row) {
                        if (row.patient && row.patient.name && row.patient.lastName) {
                            return row.patient.name + ' ' + row.patient.lastName;
                        }
                        return 'No asignado';
                    }
                },
                {
                    data: null,
                    orderable: false,
                    searchable: false,
                    render: function(data, type, row) {
                        const turnId = row.id;
                        console.log('Renderizando fila con ID:', turnId);

                        return `
                    <div class="btn-group" role="group">
                        <button id="edit_${turnId}"
                                type="button"
                                class="btn btn-primary btn-sm">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button id="delete_${turnId}"
                                type="button"
                                class="btn btn-danger btn-sm">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>`;
                    }
                }
            ],drawCallback: function(settings) {
                const table = this.api();
                const data = table.data();

                // Remover eventos anteriores
                $('button[id^="delete_"]').off();
                $('button[id^="edit_"]').off();

                // Agregar nuevos eventos
                data.each(function(row) {
                    const turnId = row.id;

                    $(`#delete_${turnId}`).on('click', function() {
                        console.log('Click en eliminar para ID:', turnId);
                        handleDelete(turnId);
                    });

                    $(`#edit_${turnId}`).on('click', function() {
                        console.log('Click en editar para ID:', turnId);
                        handleEdit(turnId);
                    });
                });
            },
            responsive: true,
            dom: 'Bfrtip',
            buttons: [
                {
                    extend: 'excel',
                    className: 'btn btn-success btn-sm',
                    text: '<i class="fas fa-file-excel"></i> Excel'
                },
                {
                    extend: 'pdf',
                    className: 'btn btn-danger btn-sm',
                    text: '<i class="fas fa-file-pdf"></i> PDF'
                },
                {
                    extend: 'print',
                    className: 'btn btn-info btn-sm',
                    text: '<i class="fas fa-print"></i> Imprimir'
                }
            ],
            order: [[0, 'asc'], [1, 'asc']],
            columnDefs: [
                { targets: -1, className: 'text-center' }  // Centra la columna de acciones
            ]
        });
    });

    function handleDelete(turnId) {
        console.log('Iniciando eliminación para turno ID:', turnId);

        if (!turnId) {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'ID de turno no válido'
            });
            return;
        }

        Swal.fire({
            title: '¿Está seguro?',
            text: "Esta acción no se puede deshacer",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                console.log('Enviando petición de eliminación para ID:', turnId);

                $.ajax({
                    url: 'SvTurnos',
                    type: 'POST',
                    data: {
                        action: 'delete',
                        id: turnId
                    },
                    success: function(response) {
                        console.log('Respuesta del servidor:', response);
                        try {
                            const jsonResponse = typeof response === 'string' ? JSON.parse(response) : response;

                            if (jsonResponse.success) {
                                Swal.fire({
                                    icon: 'success',
                                    title: '¡Éxito!',
                                    text: 'Turno eliminado correctamente'
                                }).then(() => {
                                    turnsTable.ajax.reload();
                                });
                            } else {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: jsonResponse.message || 'Error al eliminar el turno'
                                });
                            }
                        } catch (e) {
                            console.error('Error al procesar la respuesta:', e);
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: 'Error al procesar la respuesta del servidor'
                            });
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('Error en la petición:', {xhr, status, error});
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'Error al eliminar el turno'
                        });
                    }
                });
            }
        });
    }

    // Reemplazar la función handleEdit existente en doctor.jsp con esta versión:

    function handleEdit(turnId) {
        console.log('Iniciando edición para turno ID:', turnId);

        // Obtener los datos del turno actual
        const row = turnsTable.row($(`#edit_${turnId}`).closest('tr')).data();

        if (!row) {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'No se pudieron obtener los datos del turno'
            });
            return;
        }

        // Formatear la fecha para el input date
        const formattedDate = row.app_date ? row.app_date.split('T')[0] : '';
        // Formatear la hora para el input time
        const formattedTime = row.app_time ? row.app_time.substring(0, 5) : '';

        Swal.fire({
            title: 'Editar Turno',
            html: `
            <form id="editTurnForm" class="mt-3">
                <div class="mb-3">
                    <label class="form-label">Fecha</label>
                    <input type="date" class="form-control" id="editDate"
                           value="${formattedDate}" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Hora</label>
                    <input type="time" class="form-control" id="editTime"
                           value="${formattedTime}" required>
                </div>
            </form>
        `,
            showCancelButton: true,
            confirmButtonText: 'Guardar',
            cancelButtonText: 'Cancelar',
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            focusConfirm: false,
            preConfirm: () => {
                const date = document.getElementById('editDate').value;
                const time = document.getElementById('editTime').value;
                if (!date || !time) {
                    Swal.showValidationMessage('Por favor complete todos los campos');
                    return false;
                }
                return { date, time };
            }
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: 'SvTurnos',
                    type: 'POST',
                    data: {
                        action: 'update',
                        id: turnId,
                        date: result.value.date,
                        time: result.value.time
                    },
                    success: function(response) {
                        try {
                            const jsonResponse = typeof response === 'string' ? JSON.parse(response) : response;
                            if (jsonResponse.success) {
                                Swal.fire({
                                    icon: 'success',
                                    title: '¡Éxito!',
                                    text: 'Turno actualizado correctamente'
                                }).then(() => {
                                    turnsTable.ajax.reload();
                                });
                            } else {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: jsonResponse.message || 'Error al actualizar el turno'
                                });
                            }
                        } catch (e) {
                            console.error('Error al procesar la respuesta:', e);
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: 'Error al procesar la respuesta del servidor'
                            });
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('Error en la solicitud:', {xhr, status, error});
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'Error al actualizar el turno'
                        });
                    }
                });
            }
        });
    }

    function editTurn(turnId) {
        // Obtener los datos del turno actual
        const row = turnsTable.row($(`button[data-turn-id="${turnId}"]`).closest('tr')).data();

        if (!row) {
            showErrorAlert('No se pudieron obtener los datos del turno');
            return;
        }

        Swal.fire({
            title: 'Editar Turno',
            html: `
            <form id="editTurnForm" class="mt-3">
                <div class="mb-3">
                    <label class="form-label">Fecha</label>
                    <input type="date" class="form-control" id="editDate"
                           value="${row.app_date}" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Hora</label>
                    <input type="time" class="form-control" id="editTime"
                           value="${row.app_time ? row.app_time.substring(0, 5) : ''}" required>
                </div>
            </form>
        `,
            showCancelButton: true,
            confirmButtonText: 'Guardar',
            cancelButtonText: 'Cancelar',
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            focusConfirm: false,
            preConfirm: () => {
                const date = document.getElementById('editDate').value;
                const time = document.getElementById('editTime').value;
                if (!date || !time) {
                    Swal.showValidationMessage('Por favor complete todos los campos');
                    return false;
                }
                return { date, time };
            }
        }).then((result) => {
            if (result.isConfirmed) {
                saveTurnChanges(turnId, result.value.date, result.value.time);
            }
        });
    }

    // Función global para eliminar
    function deleteTurn(turnId) {
        console.log('Ejecutando deleteTurn con ID:', turnId);

        if (!turnId || turnId === 'undefined' || turnId === '') {
            console.error('ID de turno inválido:', turnId);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'ID de turno no válido'
            });
            return;
        }

        Swal.fire({
            title: '¿Está seguro?',
            text: "Esta acción no se puede deshacer",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: 'SvTurnos',
                    type: 'POST',
                    data: {
                        action: 'delete',
                        id: turnId
                    },
                    success: function(response) {
                        console.log('Respuesta del servidor:', response);
                        try {
                            // Intentar parsear la respuesta si viene como string
                            const jsonResponse = typeof response === 'string' ? JSON.parse(response) : response;

                            if (jsonResponse.success) {
                                Swal.fire({
                                    icon: 'success',
                                    title: '¡Éxito!',
                                    text: 'Turno eliminado correctamente'
                                }).then(() => {
                                    turnsTable.ajax.reload();
                                });
                            } else {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: jsonResponse.message || 'Error al eliminar el turno'
                                });
                            }
                        } catch (e) {
                            console.error('Error al procesar la respuesta:', e);
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: 'Error al procesar la respuesta del servidor'
                            });
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('Error en la petición:', {xhr, status, error});
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'Error al eliminar el turno'
                        });
                    }
                });
            }
        });
    }

    function saveTurnChanges(turnId, newDate, newTime) {
        if (!turnId) {
            showErrorAlert('ID de turno no válido');
            return;
        }

        Swal.fire({
            title: 'Guardando cambios...',
            didOpen: () => {
                Swal.showLoading();
            },
            allowOutsideClick: false,
            allowEscapeKey: false,
            showConfirmButton: false
        });

        $.ajax({
            url: 'SvTurnos',
            type: 'POST',
            data: {
                action: 'update',
                id: turnId,
                date: newDate,
                time: newTime
            },
            dataType: 'json',
            success: function(response) {
                if (response.success) {
                    Swal.fire({
                        icon: 'success',
                        title: '¡Éxito!',
                        text: response.message || 'El turno ha sido actualizado correctamente',
                        confirmButtonColor: '#3085d6'
                    }).then(() => {
                        turnsTable.ajax.reload();
                    });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: response.message || 'No se pudo actualizar el turno',
                        confirmButtonColor: '#3085d6'
                    });
                }
            },
            error: function(xhr, status, error) {
                console.error('Error en la solicitud:', {xhr, status, error});
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Error al actualizar el turno. Por favor, intente nuevamente.',
                    confirmButtonColor: '#3085d6'
                });
            }
        });
    }



    // Función auxiliar para mostrar mensajes de error
    function showErrorAlert(message) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: message,
            confirmButtonColor: '#3085d6'
        });
    }

    // Función auxiliar para mostrar mensajes de éxito
    function showSuccessAlert(message) {
        Swal.fire({
            icon: 'success',
            title: '¡Éxito!',
            text: message,
            confirmButtonColor: '#3085d6'
        });
    }

    function backToInfo() {
        document.getElementById('turns-container').style.display = 'none';
        document.getElementById('info-container').style.display = 'block';
    }

    $(document).ready(function() {
        $('#createTurnForm').on('submit', function(e) {
            e.preventDefault();

            // Obtener los valores del formulario
            const patientId = $('#patientId').val();
            const doctorId = $('#doctorId').val();
            const date = $('#date').val();
            const time = $('#time').val();

            // Mostrar SweetAlert de confirmación
            Swal.fire({
                title: '¿Confirmar turno?',
                html: `
                <div class="text-left">
                    <p><strong>Paciente ID:</strong> ${patientId}</p>
                    <p><strong>Doctor ID:</strong> ${doctorId}</p>
                    <p><strong>Fecha:</strong> ${date}</p>
                    <p><strong>Hora:</strong> ${time}</p>
                </div>
            `,
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Sí, crear turno',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                    // Si el usuario confirma, enviar la petición AJAX
                    $.ajax({
                        url: 'SvTurnos',
                        type: 'POST',
                        data: {
                            action: 'create',
                            patientId: patientId,
                            doctorId: doctorId,
                            date: date,
                            time: time
                        },
                        success: function(response) {
                            try {
                                const jsonResponse = typeof response === 'string' ? JSON.parse(response) : response;

                                if (jsonResponse.success) {
                                    Swal.fire({
                                        icon: 'success',
                                        title: '¡Éxito!',
                                        text: 'Turno creado correctamente',
                                        confirmButtonColor: '#3085d6'
                                    }).then(() => {
                                        // Limpiar el formulario
                                        $('#createTurnForm')[0].reset();
                                        // Opcional: redirigir o actualizar la tabla de turnos
                                        if (typeof turnsTable !== 'undefined') {
                                            turnsTable.ajax.reload();
                                        }
                                    });
                                } else {
                                    Swal.fire({
                                        icon: 'error',
                                        title: 'Error',
                                        text: jsonResponse.message || 'Error al crear el turno',
                                        confirmButtonColor: '#3085d6'
                                    });
                                }
                            } catch (e) {
                                console.error('Error al procesar la respuesta:', e);
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: 'Error al procesar la respuesta del servidor',
                                    confirmButtonColor: '#3085d6'
                                });
                            }
                        },
                        error: function(xhr, status, error) {
                            console.error('Error en la solicitud:', {xhr, status, error});
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: 'Error al crear el turno. Por favor, intente nuevamente.',
                                confirmButtonColor: '#3085d6'
                            });
                        }
                    });
                }
            });
        });
    });
</script>
</body>
</html>