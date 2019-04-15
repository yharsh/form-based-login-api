let isLoggedIn = false;

export const setLoggedIn = (loggedIn) => {
    isLoggedIn = loggedIn;
};

export const loggedIn = () => {
    return isLoggedIn;
};
