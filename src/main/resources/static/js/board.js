let index = {
    init: function () {
        $("#btn-save").on("click", () => {
            this.save();
        });
        $("#btn-delete").on("click", () => {
            this.deleteById();
        });
        $("#btn-updateForm").on("click", () => {
            this.updateForm();
        });
        $("#btn-update").on("click", () => {
            this.update();
        });
        $("#btn-reply-save").on("click", () => {
            this.replySave();
        });
    },

    save: function () {
        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        }
        $.ajax({
            type: "POST",
            url: "/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8"
        }).done(function () {
            swal({
                title: "글쓰기가 완료되었습니다.",
                text: "홈으로 이동합니다.",
                icon: "success"
            }).then(function () {
                location.href = "/";
            });
        }).fail(function (data, textStatus, xhr) {
            swal({
                title: data.responseText,
                icon: "error"
            });
        });
    },

    deleteById: function () {
        let id = $("#id").text();

        $.ajax({
            type: "DELETE",
            url: "/board/" + id,
            contentType: "application/json; charset=utf-8"
        }).done(function () {
            swal({
                title: "삭제가 완료되었습니다.",
                text: "홈으로 이동합니다.",
                icon: "success"
            }).then(function () {
                location.href = "/";
            });
        }).fail(function (data, textStatus, xhr) {
            swal({
                title: data.responseText,
                icon: "error"
            });
        });
    },

    updateForm: function () {
        let id = $("#board_Id").val();

        $.ajax({
            type: "GET",
            url: `/board/${id}/updateForm`

        }).done(function () {
            location.href = `/board/${id}/updateForm`;
        }).fail(function (data, textStatus, xhr) {
            swal({
                title: data.responseText,
                icon: "error"
            });
        });

    },

    update: function () {
        let id = $("#id").val();

        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
            category: $("#category").val(),
        };

        $.ajax({
            type: "PUT",
            url: "/board/" + id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
        }).done(function () {
            swal({
                title: "게시글 수정이 완료되었습니다.",
                text: "홈으로 이동합니다.",
                icon: "success"
            }).then(function () {
                location.href = "/";
            });
        }).fail(function (data, textStatus, xhr) {
            swal({
                title: data.responseText,
                icon: "error"
            });
        });
    },

    replySave: function () {
        let data = {
            content: $("#reply-content").val(),
        };
        let boardId = $("#boardId").val();
        $.ajax({
            type: "POST",
            url: `/board/${boardId}/reply`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
        }).done(function () {
            location.href = `/board/${boardId}`;
        }).fail(function (data, textStatus, xhr) {
            swal({
                title: data.responseText,
                icon: "error"
            });
        });
    },

    replyDelete: function (boardId, replyId) {
        $.ajax({
            type: "DELETE",
            url: `/board/reply/${replyId}`,
        }).done(function () {
            location.href = `/board/${boardId}`;
        }).fail(function (data, textStatus, xhr) {
            swal({
                title: data.responseText,
                icon: "error"
            });
        });
    },
}


index.init();
