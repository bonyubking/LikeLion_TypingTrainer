import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./SignupPage.module.css";
import common from "../../styles/common.module.css";
import { checkNickname, checkEmail, signup } from "../../api/api";
import people from "../../assets/img/people.png";

const SignupPage = () => {
  const navigate = useNavigate(); // 페이지 이동
  const [showPassword, setShowPassword] = useState(false); // 비밀번호 보임 여부
  const [showConfirmPassword, setShowConfirmPassword] = useState(false); // 비밀번호 확인 보임 여부
  const [isEmailAvailable, setIsEmailAvailable] = useState(false); // 이메일 중복 확인 여부
  const [isNicknameAvailable, setIsNicknameAvailable] = useState(false); // 닉네임 중복 확인 여부
  /**회원가입 입력값 데이터 */
  const [formData, setFormData] = useState({
    email: "",
    nickname: "",
    password: "",
    confirmPassword: "",
  });

  /**입력값 저장 */
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  /**닉네임 중복 확인 */
  const checkNicknameAvailability = async () => {
    try {
      const response = await checkNickname(formData.nickname);
      setIsNicknameAvailable(true);
      alert(response.message);
    } catch (error) {
      setIsNicknameAvailable(false);
      alert(
        error.message ? error.message : "닉네임 확인 중 오류가 발생했습니다."
      );
    }
  };

  /**아이디 중복 확인 */
  const checkEmailAvailability = async () => {
    try {
      const response = await checkEmail(formData.email);
      setIsEmailAvailable(true);
      alert(response.message);
    } catch (error) {
      setIsEmailAvailable(false);
      alert(
        error.message ? error.message : "아이디 확인 중 오류가 발생했습니다."
      );
    }
  };

  /**회원가입 제출 */
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!isEmailAvailable || !isNicknameAvailable) {
      alert("이메일과 닉네임 중복 확인이 필요합니다.");
      return;
    }

    if (formData.password !== formData.confirmPassword) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }

    const response = await signup(
      formData.email,
      formData.nickname,
      formData.password
    );
    if (response.status === 200) { 
      alert("회원가입이 완료되었습니다.\n로그인 페이지로 이동합니다.");
      navigate("/login");
    } else { 
      alert(response.message);
    }
  };

  return (
    <div className={common.container}>
      <div className={styles.main}>
        <div className={styles.illustration}>
          <img src={people} alt="사람들 일러스트" />
        </div>

        <div className={styles.form_card}>
          <div className={styles.form_card_header}>
            <h3>안녕!</h3>
            <h2>회원가입</h2>
            <p>우리와 함께해요</p>
          </div>
          <form onSubmit={handleSubmit}>
            <div className={styles.form_group}>
              <label>ID</label>
              <div className={styles.id_nickname_input}>
                <input
                  type="email"
                  name="email"
                  value={formData.email}
                  onChange={handleInputChange}
                  placeholder="Enter your email"
                  disabled={isEmailAvailable}
                  className={isEmailAvailable ? styles.verified_input : ""}
                />
                <div className={styles.id_nickname_check}>
                  <span onClick={checkEmailAvailability}>확인</span>
                </div>
              </div>
            </div>

            <div className={styles.form_group}>
              <label>NICKNAME</label>
              <div className={styles.id_nickname_input}>
                <input
                  type="text"
                  name="nickname"
                  value={formData.nickname}
                  onChange={handleInputChange}
                  placeholder="Enter your user name"
                  disabled={isNicknameAvailable}
                  className={isNicknameAvailable ? styles.verified_input : ""}
                />
                <div className={styles.id_nickname_check}>
                  <span onClick={checkNicknameAvailability}>확인</span>
                </div>
              </div>
            </div>

            <div className={styles.form_group}>
              <label>Password</label>
              <div className={styles.password_input}>
                <input
                  type={showPassword ? "text" : "password"}
                  name="password"
                  value={formData.password}
                  onChange={handleInputChange}
                  placeholder="Enter your Password"
                />
                <span className={styles.toggle_password} onClick={() => setShowPassword(!showPassword)}>
                  {showPassword ? "👁️" : "👁️‍🗨️"}
                </span>
              </div>
            </div>

            <div className={styles.form_group}>
              <label>Confirm Password</label>
              <div className={styles.password_input}>
                <input
                  type={showConfirmPassword ? "text" : "password"}
                  name="confirmPassword"
                  value={formData.confirmPassword}
                  onChange={handleInputChange}
                  placeholder="Confirm your Password"
                />
                <span
                  className={styles.toggle_password}
                  onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                >
                  {showConfirmPassword ? "👁️" : "👁️‍🗨️"}
                </span>
              </div>
            </div>

            <button
              type="submit"
              className={`${styles.signup_button} ${
                !isEmailAvailable || // 이메일 중복 확인 여부
                !isNicknameAvailable || // 닉네임 중복 확인 여부
                formData.password !== formData.confirmPassword || // 비밀번호 일치 여부
                !formData.password // 비밀번호 입력 여부
                  ? styles.disabled_button
                  : ""
              }`}
              disabled={
                !isEmailAvailable ||
                !isNicknameAvailable ||
                formData.password !== formData.confirmPassword ||
                !formData.password
              }
            >
              회원가입
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default SignupPage;
