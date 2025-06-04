document.getElementById("login-form").addEventListener("submit", function (e) {
    e.preventDefault();
    const data = Object.fromEntries(new FormData(e.target));

    fetch("/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
        credentials: "include"
    })
        .then(res => {
            if (!res.ok) throw new Error("로그인 실패");
            return res.json();
        })
        .then(() => {
            location.href = "../html/schedule.html";  // ✅ 한 번만 실행
        })
        .catch(err => alert(err.message));
});
