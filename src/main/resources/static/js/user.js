let index = {
    init: function () {
        $("#btn-save").on("click", () => {
            this.save();
        });
        $("#btn-login").on("click", () => {
            this.login();
        });
        $("#btn-update").on("click", () => {
            this.update();
        });
    },
    save: function () {
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        }
        $.ajax({
            type: "POST",
            url: "/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8"
        }).done(function () {
            swal({
                title: "회원가입 되었습니다.",
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

    login: function () {
        let rsa = new RSAKey();
        rsa.setPublic($('#RSAModulus').val(), $('#RSAExponent').val());

        let data = {
            username: rsa.encrypt($("#username").val()),
            password: rsa.encrypt($("#password").val())
        };

        $.ajax({
            type: "POST",
            url: "/login",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8"
        }).done(function () {
            swal({
                title: "로그인 되었습니다.",
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
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        }
        $.ajax({
            type: "PUT",
            url: "/user/" + id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
        }).done(function () {
            swal({
                title: "회원 수정이 완료되었습니다.",
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
    }
}
index.init();
