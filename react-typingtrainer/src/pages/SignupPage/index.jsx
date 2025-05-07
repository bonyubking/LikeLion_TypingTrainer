import React, { useState } from "react";
import styles from "./SignupPage.module.css";
import common from "../../styles/common.module.css";

const SignupPage = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  return (
    <div className={common.container}>
      <div className={styles.main}>
        <div className={styles.illustration}>
          <img src="/img/people.png" alt="사람들 일러스트" />
        </div>

        <div className={styles.form_card}>
          <div className={styles.form_card_header}>
            <h3>안녕!</h3>
            <h2>회원가입</h2>
            <p>우리와 함께해요</p>
          </div>
          <form>
            <div className={styles.form_group}>
              <label>ID</label>
              <div className={styles.id_nickname_input}>
              <input type="email" placeholder="Enter your email" />
              <div className={styles.id_nickname_check}>
                <span>확인</span>
              </div>
              </div>
              
            </div>

            <div className={styles.form_group}>
              <label>NICKNAME</label>
              <div className={styles.id_nickname_input}>
              <input type="text" placeholder="Enter your user name" />
              <div className={styles.id_nickname_check}>
                <span>확인</span>
              </div>
              </div>
            </div>

            <div className={styles.form_group}>
              <label>Password</label>
              <div className={styles.password_input}>
                <input
                  type={showPassword ? "text" : "password"}
                  placeholder="Enter your Password"
                />
                <span onClick={() => setShowPassword(!showPassword)}>
                  {showPassword ? "👁️" : "👁️‍🗨️"}
                </span>
              </div>
            </div>

            <div className={styles.form_group}>
              <label>Confirm Password</label>
              <div className={styles.password_input}>
                <input
                  type={showConfirmPassword ? "text" : "password"}
                  placeholder="Confirm your Password"
                />
                <span onClick={() => setShowPassword(!showPassword)}>
                  {showPassword ? "👁️" : "👁️‍🗨️"}
                </span>
              </div>
            </div>

            <button type="submit" className={styles.signup_button}>
              회원가입
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default SignupPage;
