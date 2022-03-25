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
            alert("회원가입이 완료되었습니다.");
            location.href = "/";
        }).fail(function (data, textStatus, xhr) {
            alert(data.responseText);
        });

    },

    login: function () {
        let data = {
            username: $("#username").val(),
            password: $("#password").val()
        }
        $.ajax({
            type: "POST",
            url: "/login",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8"
        }).done(function () {
            alert("로그인이 완료되었습니다.");
            location.href = "/";
        }).fail(function (data, textStatus, xhr) {
            alert(data.responseText);
        });
    },

    update: function () {
        let data = {
            id: $("#id").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        }
        $.ajax({
            type: "PUT",
            url: "/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
        }).done(function (res) {
            alert("회원수정이 완료되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}
index.init();
