$(document).ready(function () {
    // $('body').bootstrapMaterialDesign();
    var accountTable = $('#account-table').DataTable();

    $('#account-table tbody').on('click', 'tr', function () {
        $(this).toggleClass('selected');
        $("#delete-sel").text("Delete (" + accountTable.rows('.selected').data().length + ")");
        $("#deactive-sel").text("Deactive (" + accountTable.rows('.selected').data().length + ")");
    });
});