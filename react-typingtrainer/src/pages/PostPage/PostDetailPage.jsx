import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchPostById, fetchCommentsbyId, createComment } from '../../api/api';
import styles from './postdetail.module.css';

function PostDetailPage() {
  const userId = 1;
  const { postId } = useParams();
  const navigate = useNavigate();
  const [post, setPost] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [comments, setComments] = useState([]); // 댓글 목록
  const [newComment, setNewComment] = useState(''); // 새 댓글 입력값

  const loadComments = async () => {
    try {
      const data = await fetchCommentsbyId(postId);
      setComments(data);
    } catch (err) {
      console.error('댓글 로드 실패:', err);
    }
  };

  const handleSubmitComment = async () => {
    if (!newComment.trim()) return;
    
    try {
      await createComment({
        userId: userId, // 실제 구현시 로그인된 사용자 ID로 대체
        content: newComment,
        postId: postId
      });
      setNewComment(''); // 입력창 초기화
      loadComments(); // 댓글 목록 새로고침
    } catch (err) {
      console.error('댓글 등록 실패:', err);
    }
  };

  useEffect(() => {
    const loadPost = async () => {
      try {
        const data = await fetchPostById(postId);
        setPost(data);
        setLoading(false);
        loadComments(); // 게시글 로드 시 댓글도 함께 조회
      } catch (err) {
        setError('게시물을 불러오는데 실패했습니다.');
        setLoading(false);
      }
    };
  
    loadPost();
  }, [postId]);

  if (loading) {
    return <div>게시물을 불러오는 중...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  if (!post) {
    return <div>게시물을 찾을 수 없습니다.</div>;
  }

  return (
    <div className={styles.container}>
      <div className={styles.windowControls}>
        <span className={`${styles.circle} ${styles.red}`} />
        <span className={`${styles.circle} ${styles.yellow}`} />
        <span className={`${styles.circle} ${styles.green}`} />
      </div>      
      <div className={styles.detailBox}>
      <button className={styles.backButton} 
        onClick={() => navigate('/post')}>
      ← 목록으로
      </button>

        <h1 className={styles.title}>
          {post.title}
          </h1>
        <div className= {styles.content}>
          
          {post.content}
        </div>
      </div>

      <div className={styles.commentsSection}>
        <h3>댓글 목록</h3>
        {comments.map((comment) => (
          <div key={comment.commentId} className={styles.commentRow}>
            {/* 왼쪽 네모: USER ID */}
            <div className={styles.avatarBox}>
              {comment.uid}
            </div>

            {/* 오른쪽 댓글 박스 */}
            <div className={styles.commentBox}>
              <p className={styles.commentText}>
                {comment.content}
              </p>
              <div className={styles.commentMeta}>
                {comment.createdAt}
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* 댓글 입력 */}
      <div className={styles.inputSection}>
        <input
          type="text"
          className={styles.textBox}
          placeholder="댓글을 입력하세요"
          value={newComment}
          onChange={(e) => setNewComment(e.target.value)}
        />
        <button
          className={styles.inputButton}
          onClick={handleSubmitComment}
        >
          등록하기
        </button>
      </div>
    </div>
  );
}

export default PostDetailPage;  
