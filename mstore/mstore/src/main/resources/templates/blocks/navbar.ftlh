<nav class="navbar navbar-dark bg-dark">
    <div class="container-fluid">
        <a href="/">
            <span class="navbar-brand mb-0 h1">mstore</span>
        </a>
        <#if user??>
            <div class="dropdown text-end">
                <button class="btn btn-light my-2 my-sm-0" type="button"
                        onclick="window.location.href = '/profile'">
                    Профиль
                </button>
            </div>
        <#else>
            <button class="btn btn-light my-2 my-sm-0" type="button"
                    onclick="window.location.href = '/login'">
                Войти
            </button>
        </#if>
    </div>
</nav>