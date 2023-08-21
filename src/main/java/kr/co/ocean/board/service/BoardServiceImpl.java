package kr.co.ocean.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.ocean.board.dao.BoardMapper;
import kr.co.ocean.board.dto.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper boardMapper;
	
	public BoardServiceImpl(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}
	
	@Override
	public List<BoardVO> getEnableBoardList() throws Exception {
		// TODO Auto-generated method stub
		return boardMapper.selectEnableBoards();
	}

	@Override
	public void setBoard(BoardVO vo) throws Exception {
		boardMapper.insertBoard(vo);
	}

	@Override
	public void disableBoard(BoardVO vo) throws Exception {
		boardMapper.disableBoard(vo);
	}

	@Override
	public List<BoardVO> getBoard(Map param) throws Exception {
		return boardMapper.selectDetailBaord(param);
	}

	@Override
	public List<BoardVO> getBoardList(Map param) throws Exception {
		// TODO Auto-generated method stub
		return boardMapper.selectPagingBoards(param);
	}

	@Override
	public int getCount(Map param) throws Exception {	
		return boardMapper.selectBoardsCount(param);
	}

	@Override
	public void updateBoard(BoardVO vo) throws Exception {
		boardMapper.updateBoard(vo);
	}

}
