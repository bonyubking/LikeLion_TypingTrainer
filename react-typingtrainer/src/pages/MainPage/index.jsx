import React from "react";
import styles from "./MainPage.module.css";
import common from "../../styles/common.module.css";

const MainPage = () => {
  return (
    <div className={common.container}>
      <h1 className={styles.title}>타자 연습</h1>
      <p className={styles.subtitle}>
        Start Practicing typing and write it down
      </p>
    </div>
  );
};

export default MainPage;
