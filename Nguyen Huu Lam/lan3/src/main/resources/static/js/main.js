$(document).ready(function () {
    $("#deleteBtn").click(function (ev) {
        var userID = $(this).attr('user-id');

        $.ajax({
            url: "/user?id=" + userID,
            type: "DELETE",
            dataType: 'json',
            success: function () {
                location.reload();
            },
            error: function (err) {
                console.log(err);
                alert(userID);
            }
        });
    });

    var selectedUsers = [];
    $('#user-table tbody tr').on('change', function (e) {
        var row = $(e.currentTarget);
        var userId = row.find("button").attr("user-id");

        if (row.hasClass("is-selected")) {
            selectedUsers.push(userId);
        } else {
            var userIDpos = selectedUsers.indexOf(userId);
            if (userIDpos > -1) { // found user id in selected users
                selectedUsers.splice(userIDpos, 1);
            }
        }

        // console.log(selectedUsers);
        if (selectedUsers.length) {
            $("#btn-delete-all").prop('disabled', false);
            $("#btn-delete-all").text("DELETE (" + selectedUsers.length + ")");
        } else {
            $("#btn-delete-all").prop('disabled', true);
            $("#btn-delete-all").text("DELETE");
        }
    });

    $("#btn-delete-all").click(function (ev) {
        console.log(selectedUsers);
        $.ajax({
            url: "/users?ids=" + selectedUsers.join(","),
            type: "DELETE",
            dataType: 'json',
            success: function () {
                location.reload();
            },
            error: function (err) {
                console.log(err);
                // alert(userID);
            }
        });
    });



    // Selecting all Buttons with data-toggle="modal", i.e. the modal triggers on the page
    var modalTriggers = document.querySelectorAll('[data-toggle="modal"]');

// Getting the target modal of every button and applying listeners
    for (var i = modalTriggers.length - 1; i >= 0; i--) {
        var t = modalTriggers[i].getAttribute('data-target');
        var id = '#' + modalTriggers[i].getAttribute('id');

        modalProcess(t, id);
    }

    /**
     * It applies the listeners to modal and modal triggers
     * @param  {string} selector [The Dialog ID]
     * @param  {string} button   [The Dialog triggering Button ID]
     */
    function modalProcess(selector, button) {
        var dialog = document.querySelector(selector);
        var showDialogButton = document.querySelector(button);

        if (dialog) {
            if (!dialog.showModal) {
                dialogPolyfill.registerDialog(dialog);
            }
            showDialogButton.addEventListener('click', function () {
                dialog.showModal();
            });
            dialog.querySelector('.close').addEventListener('click', function () {
                dialog.close();
            });
        }
    }
});