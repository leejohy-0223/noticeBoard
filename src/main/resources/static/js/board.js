let index = {
    init: function () {
        $("#btn-save").on("click", () => {
            this.save();
        });
        $("#btn-delete").on("click", () => {
            this.deleteById();
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

    update: function () {
        let id = $("#id").val();

        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
            category:$("#category").val(),
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
            // 회원가입 수행 요청
            type: "POST",
            url: `/board/${boardId}/reply`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
        }).done(function () {
            swal({
                title: "댓글 작성이 완료되었습니다.",
                text: "해당 게시글로 이동합니다.",
                icon: "success"
            }).then(function () {
                location.href = `/board/${boardId}`;
            });
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
            url: `/board/${boardId}/reply/${replyId}`,
        }).done(function () {
            swal({
                title: "댓글 삭제가 완료되었습니다.",
                text: "해당 게시글로 이동합니다.",
                icon: "success"
            }).then(function () {
                location.href = `/board/${boardId}`;
            });
        }).fail(function (data, textStatus, xhr) {
            swal({
                title: data.responseText,
                icon: "error"
            });
        });

    },
}


index.init();
