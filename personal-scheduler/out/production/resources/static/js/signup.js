document.getElementById("signup-form").addEventListener("submit", function (e) {
    e.preventDefault();
    const data = Object.fromEntries(new FormData(e.target));

    fetch("/api/signup", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
        .then(res => {
            if (!res.ok) throw new Error("회원가입 실패");
            return res.json();
        })
        .then(() => {
            alert("회원가입 성공! 로그인 페이지로 이동합니다.");
            location.href = "login.html";  // 같은 html 디렉토리 내
        })
        .catch(err => alert(err.message));
});
