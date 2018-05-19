package Test;

import ia.entities.Spell;
import org.junit.Assert;
import org.junit.Test;
import utils.GameData;
import utils.d2i.d2iManager;

import java.util.List;

public class FightTest {

    @Test
    public void getSpellIdTest(){
        Assert.assertEquals(803,GameData.getSpellId(161,3));
    }

    @Test
    public void getSpellTest(){
        new d2iManager(GameData.getPathDatBot() + "\\DatBot.Interface\\utils\\gamedata\\i18n_fr.d2i");
        Spell spell = GameData.getSpell(161,3);
        Assert.assertEquals(803, spell.getId());
        Assert.assertEquals(12, spell.getRange());
        Assert.assertEquals(15, spell.getCriticalHitProbability());
        Assert.assertEquals(3, spell.getGrade());
        Assert.assertEquals(2, spell.getCriticalEffect().length);
        Assert.assertEquals(2, spell.getEffects().length);
        Assert.assertEquals(22, spell.getCriticalEffect()[0].getDiceNum());
        Assert.assertEquals(320, spell.getCriticalEffect()[1].getEffectId());
        Assert.assertEquals("Flèche Magique", spell.getName());
    }

    @Test
    public void getMonsterSpellsTest(){
        List<Spell> spells = GameData.getMonsterSpells(489,1);
        System.out.println(spells.get(0));
        Assert.assertEquals(2,spells.size());
        Assert.assertEquals("Picore", spells.get(0).getName());
        Assert.assertEquals(121145, spells.get(1).getCriticalEffect()[0].getEffectUid());
    }
}
