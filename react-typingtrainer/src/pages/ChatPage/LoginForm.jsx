import React from "react";
import styles from "./LoginForm.module.css";

const LoginForm = () => (
  <form className={styles.login_form}>
    <input className={styles.input} placeholder="아이디" />
    <input className={styles.input} type="password" placeholder="비밀번호" />
    <button className={styles.login_button}>로그인</button>
  </form>
);

export default LoginForm;