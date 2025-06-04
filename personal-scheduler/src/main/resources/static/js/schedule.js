const apiBase = "/api/schedules";
let currentFilter = "all";

window.onload = () => {
    checkLogin(); // 🔒 세션 검사 먼저
    loadSchedules("all");
    document.getElementById("schedule-form").addEventListener("submit", handleSubmit);
};

function checkLogin() {
    fetch("/api/me", { credentials: "include" })
        .then(res => {
            if (!res.ok) throw new Error("로그인이 필요합니다.");
            return res.json();
        })
        .catch(() => {
            alert("로그인이 필요합니다.");
            window.location.href = "login.html";
        });
}

function loadSchedules(filter) {
    currentFilter = filter;

    const sectionTitle = {
        all: "보관함",
        today: "오늘",
        future: "미래",
        past: "과거"
    }[filter];

    document.getElementById("section-title").textContent = sectionTitle;

    fetchWithCredentials(`${apiBase}?filter=${filter}`)
        .then(res => res.json())
        .then(data => {
            console.log("📦 받은 데이터:", data);
            renderScheduleList(data.data); // ✅ 여기 핵심
        });
}

function renderScheduleList(data) {
    const list = document.getElementById("schedule-list");
    list.innerHTML = "";

    data.forEach(s => {
        const item = document.createElement("li");

        const span = document.createElement("span");
        span.innerHTML = `<strong>${escapeHTML(s.title)}</strong> (${s.startDate} ~ ${s.endDate})`;

        const editBtn = document.createElement("button");
        editBtn.textContent = "수정";
        editBtn.addEventListener("click", () => {
            editSchedule(s.id, s.title, s.description, s.startDate, s.endDate);
        });

        const delBtn = document.createElement("button");
        delBtn.textContent = "삭제";
        delBtn.addEventListener("click", () => {
            deleteSchedule(s.id);
        });

        const actions = document.createElement("div");
        actions.className = "actions";
        actions.appendChild(editBtn);
        actions.appendChild(delBtn);

        item.appendChild(span);
        item.appendChild(actions);
        list.appendChild(item);
    });
}

function openModal(isEdit = false) {
    document.getElementById("modal").classList.remove("hidden");
    document.getElementById("modal-title").textContent = isEdit ? "일정 수정" : "일정 추가";
}

function closeModal() {
    document.getElementById("modal").classList.add("hidden");
    document.getElementById("schedule-form").reset();
    document.getElementById("scheduleId").value = "";
}

function handleSubmit(e) {
    e.preventDefault();

    const id = document.getElementById("scheduleId").value;
    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;

    const schedule = { title, description, startDate, endDate };

    const method = id ? "PUT" : "POST";
    const url = id ? `${apiBase}/${id}` : apiBase;

    fetchWithCredentials(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(schedule)
    })
        .then(res => res.json())
        .then(() => {
            closeModal();
            loadSchedules(currentFilter);
        });
}

function deleteSchedule(id) {
    fetchWithCredentials(`${apiBase}/${id}`, {
        method: "DELETE"
    }).then(() => loadSchedules(currentFilter));
}

function editSchedule(id, title, description, startDate, endDate) {
    document.getElementById("scheduleId").value = id;
    document.getElementById("title").value = title;
    document.getElementById("description").value = description;
    document.getElementById("startDate").value = startDate;
    document.getElementById("endDate").value = endDate;
    openModal(true);
}

function escapeHTML(str) {
    if (!str) return "";
    return str.replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}

function fetchWithCredentials(url, options = {}) {
    return fetch(url, {
        ...options,
        credentials: "include"
    });
}
