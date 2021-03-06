package protocol.network.messages.game.context.roleplay.paddock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.NetworkMessage;
import protocol.network.types.game.paddock.PaddockInformationsForSell;

@SuppressWarnings("unused")
public class PaddockToSellListMessage extends NetworkMessage {
	public static final int ProtocolId = 6138;

	private int pageIndex;
	private int totalPage;
	private List<PaddockInformationsForSell> paddockList;

	public int getPageIndex() { return this.pageIndex; }
	public void setPageIndex(int pageIndex) { this.pageIndex = pageIndex; };
	public int getTotalPage() { return this.totalPage; }
	public void setTotalPage(int totalPage) { this.totalPage = totalPage; };
	public List<PaddockInformationsForSell> getPaddockList() { return this.paddockList; }
	public void setPaddockList(List<PaddockInformationsForSell> paddockList) { this.paddockList = paddockList; };

	public PaddockToSellListMessage(){
	}

	public PaddockToSellListMessage(int pageIndex, int totalPage, List<PaddockInformationsForSell> paddockList){
		this.pageIndex = pageIndex;
		this.totalPage = totalPage;
		this.paddockList = paddockList;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.pageIndex);
			writer.writeVarShort(this.totalPage);
			writer.writeShort(this.paddockList.size());
			int _loc2_ = 0;
			while( _loc2_ < this.paddockList.size()){
				this.paddockList.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.pageIndex = reader.readVarShort();
			this.totalPage = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.paddockList = new ArrayList<PaddockInformationsForSell>();
			while( _loc3_ <  _loc2_){
				PaddockInformationsForSell _loc15_ = new PaddockInformationsForSell();
				_loc15_.Deserialize(reader);
				this.paddockList.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
