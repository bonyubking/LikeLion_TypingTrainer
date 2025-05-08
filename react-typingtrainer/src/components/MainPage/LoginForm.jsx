import React, { useState } from "react";
import styles from "./LoginForm.module.css";
import { useNavigate } from "react-router-dom";
import { login } from "../../api/api";
import people from "../../assets/img/people_blue.png";

const LoginForm = ({ isLoggedIn, setIsLoggedIn }) => {
  const [form, setForm] = useState({ email: "", password: "" });
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [userNickname, setUserNickname] = useState(sessionStorage.getItem('nickname') || "");
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await login(form.email, form.password);
      // sessionStorage에 사용자 정보 저장
      sessionStorage.setItem('nickname', response.nickname);
      sessionStorage.setItem('userId', response.userId);
      sessionStorage.setItem('isLoggedIn', true);
      
      setUserNickname(response.nickname);
      setIsLoggedIn(true);
      alert("로그인 성공!");
    } catch (error) {
      alert(error.message || "로그인 실패");
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    sessionStorage.clear();
    setIsLoggedIn(false);
    setUserNickname("");
    setForm({ email: "", password: "" });
    alert("로그아웃 되었습니다.");
    navigate("/");
  };

  if (isLoggedIn) {
    const today = new Date();
    const formattedDate = today.toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      weekday: 'long',
    });
    return (
      <div className={styles.login_form_wrap}>
        <div className={styles.form_card}>
          <div className={styles.welcome_message}>
            <div className={styles.welcome_date}>{formattedDate}</div>
            <h2>반갑습니다. {userNickname}님!</h2>
            <p className={styles.welcome_sub}>
              오늘도 즐거운 타자연습 되세요!<br />
              기록을 남기고, 실력을 키워보세요 🎯
            </p>
            <div className={styles.logout}>
            <p className={styles.logout_text} onClick={handleLogout}>로그아웃</p>
          </div> 
          </div>

        </div>
        <div className={styles.login_form_illustration}>
          <img src={people} alt="사람들 일러스트" />
        </div>
      </div>
    );
  }

  return (
    <div className={styles.login_form_wrap}>
      <div className={styles.form_card}>
        <div className={styles.form_card_header}>
          <h3>안녕</h3>
          <h2>로그인</h2>
          <p>세상에서 제일 빠른 로그인</p>
        </div>
        <form className={styles.login_form} onSubmit={handleSubmit}>
          <div className={styles.form_group}>
            <label htmlFor="email">ID</label>
            <input
              id="email"
              name="email"
              type="text"
              placeholder="Enter your email"
              value={form.email}
              onChange={handleChange}
              required
            />
          </div>

          <div className={styles.form_group}>
            <label htmlFor="password">Password</label>
            <div className={styles.password_field}>
              <input
                id="password"
                name="password"
                type={showPassword ? "text" : "password"}
                placeholder="Please Enter your password"
                value={form.password}
                onChange={handleChange}
                required
              />
              <span
                className={styles.toggle_password}
                onClick={() => setShowPassword((prev) => !prev)}
              >
                {showPassword ? "👁️" : "🙈"}
              </span>
            </div>
          </div>

          <button
            type="submit"
            className={styles.login_button}
            disabled={loading}
          >
            로그인
          </button>

          <div className={styles.signup_link}>
            <span>계정이 없으신가요? </span>
            <a href="/signup">등록하기</a>
          </div>
        </form>
      </div>
      <div className={styles.login_form_illustration}>
        <img src={people} alt="사람들 일러스트" />
      </div>
    </div>
  );
};

export default LoginForm;
