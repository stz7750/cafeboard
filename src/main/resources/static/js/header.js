    document.addEventListener("DOMContentLoaded", function() {
        const searchBtn = document.getElementById("searchBtn");
        const searchValueInput = document.getElementById("searchValue");

        // 검색 버튼 클릭 시
        searchBtn.addEventListener("click", function() {
            searchResult();
        });

        // 엔터 키 감지 시
        searchValueInput.addEventListener("keydown", function(event) {
            if (event.key === "Enter") {
                event.preventDefault(); // 브라우저의 기본 동작 막기
                searchResult();
            }
        });
    });

    function searchResult() {
        const searchType = document.getElementById("searchType").value;
        const searchValue = document.getElementById("searchValue").value;
        const url = "/content/searchContent?searchType=" + searchType + "&searchValue=" + searchValue;
        window.location.href = url;
    }
