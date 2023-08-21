package kr.co.ocean.board.service;

import java.util.List;
import java.util.Map;

import kr.co.ocean.board.dto.BoardVO;

public interface BoardService {
	public List<BoardVO> getEnableBoardList() throws Exception;

	public List<BoardVO> getBoard(Map param) throws Exception;

	public void setBoard(BoardVO vo) throws Exception;

	public void disableBoard(BoardVO vo) throws Exception;

	public List<BoardVO> getBoardList(Map param) throws Exception;

	public int getCount(Map param) throws Exception;
	
	public void updateBoard(BoardVO vo) throws Exception;
}
