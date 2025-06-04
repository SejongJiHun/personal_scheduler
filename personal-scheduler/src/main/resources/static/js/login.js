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
            location.href = "schedule.html";  // 같은 html 디렉토리 내이므로 상대 경로
        })
        .catch(err => alert(err.message));
});
