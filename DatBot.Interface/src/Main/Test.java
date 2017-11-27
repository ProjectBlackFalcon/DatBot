package Main;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import Game.Info;
import Game.movement.CellMovement;
import Game.movement.Movement;
import protocol.network.messages.game.context.roleplay.CurrentMapMessage;
import protocol.network.util.DofusDataReader;
import utils.Astar;
import utils.JSON;

public class Test {

	public static void main(String[] args) throws NumberFormatException, Exception {
		System.out.println(32 & 16);
	}
	
	private static void sendToModel(String botInstance, String msgId,String dest, String msgType, String command, Object [] param){
		String newParam = "";
		for(int i = 0 ; i < param.length ; i++){
			if(i == param.length - 1){
				newParam += param[i];
			} else {
				newParam += param[i] + ",";
			}
		}
		System.out.println(String.format("%s;%s;%s;%s;%s;[%s]",botInstance,msgId,dest,msgType,command,newParam));
	}

}
