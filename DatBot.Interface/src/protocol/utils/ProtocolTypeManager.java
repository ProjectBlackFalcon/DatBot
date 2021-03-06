package protocol.utils;

import protocol.network.Network;
import protocol.network.NetworkMessage;
import protocol.network.types.common.basic.StatisticData;
import protocol.network.types.common.basic.StatisticDataBoolean;
import protocol.network.types.common.basic.StatisticDataByte;
import protocol.network.types.common.basic.StatisticDataInt;
import protocol.network.types.common.basic.StatisticDataShort;
import protocol.network.types.common.basic.StatisticDataString;
import protocol.network.types.game.achievement.AchievementAchieved;
import protocol.network.types.game.achievement.AchievementAchievedRewardable;
import protocol.network.types.game.actions.fight.AbstractFightDispellableEffect;
import protocol.network.types.game.actions.fight.FightTemporaryBoostEffect;
import protocol.network.types.game.actions.fight.FightTemporaryBoostStateEffect;
import protocol.network.types.game.actions.fight.FightTemporaryBoostWeaponDamagesEffect;
import protocol.network.types.game.actions.fight.FightTemporarySpellBoostEffect;
import protocol.network.types.game.actions.fight.FightTemporarySpellImmunityEffect;
import protocol.network.types.game.actions.fight.FightTriggeredEffect;
import protocol.network.types.game.approach.ServerSessionConstant;
import protocol.network.types.game.approach.ServerSessionConstantInteger;
import protocol.network.types.game.approach.ServerSessionConstantLong;
import protocol.network.types.game.approach.ServerSessionConstantString;
import protocol.network.types.game.character.CharacterMinimalAllianceInformations;
import protocol.network.types.game.character.CharacterMinimalGuildInformations;
import protocol.network.types.game.character.CharacterMinimalPlusLookAndGradeInformations;
import protocol.network.types.game.character.CharacterMinimalPlusLookInformations;
import protocol.network.types.game.character.choice.CharacterBaseInformations;
import protocol.network.types.game.character.choice.CharacterHardcoreOrEpicInformations;
import protocol.network.types.game.character.status.PlayerStatus;
import protocol.network.types.game.character.status.PlayerStatusExtended;
import protocol.network.types.game.context.EntityDispositionInformations;
import protocol.network.types.game.context.FightEntityDispositionInformations;
import protocol.network.types.game.context.GameContextActorInformations;
import protocol.network.types.game.context.GameRolePlayTaxCollectorInformations;
import protocol.network.types.game.context.IdentifiedEntityDispositionInformations;
import protocol.network.types.game.context.MapCoordinates;
import protocol.network.types.game.context.MapCoordinatesAndId;
import protocol.network.types.game.context.MapCoordinatesExtended;
import protocol.network.types.game.context.TaxCollectorStaticExtendedInformations;
import protocol.network.types.game.context.TaxCollectorStaticInformations;
import protocol.network.types.game.context.fight.FightAllianceTeamInformations;
import protocol.network.types.game.context.fight.FightResultAdditionalData;
import protocol.network.types.game.context.fight.FightResultExperienceData;
import protocol.network.types.game.context.fight.FightResultFighterListEntry;
import protocol.network.types.game.context.fight.FightResultListEntry;
import protocol.network.types.game.context.fight.FightResultMutantListEntry;
import protocol.network.types.game.context.fight.FightResultPlayerListEntry;
import protocol.network.types.game.context.fight.FightResultPvpData;
import protocol.network.types.game.context.fight.FightResultTaxCollectorListEntry;
import protocol.network.types.game.context.fight.FightTeamInformations;
import protocol.network.types.game.context.fight.FightTeamMemberCharacterInformations;
import protocol.network.types.game.context.fight.FightTeamMemberCompanionInformations;
import protocol.network.types.game.context.fight.FightTeamMemberInformations;
import protocol.network.types.game.context.fight.FightTeamMemberMonsterInformations;
import protocol.network.types.game.context.fight.FightTeamMemberTaxCollectorInformations;
import protocol.network.types.game.context.fight.FightTeamMemberWithAllianceCharacterInformations;
import protocol.network.types.game.context.fight.GameFightAIInformations;
import protocol.network.types.game.context.fight.GameFightCharacterInformations;
import protocol.network.types.game.context.fight.GameFightCompanionInformations;
import protocol.network.types.game.context.fight.GameFightFighterCompanionLightInformations;
import protocol.network.types.game.context.fight.GameFightFighterInformations;
import protocol.network.types.game.context.fight.GameFightFighterLightInformations;
import protocol.network.types.game.context.fight.GameFightFighterMonsterLightInformations;
import protocol.network.types.game.context.fight.GameFightFighterNamedInformations;
import protocol.network.types.game.context.fight.GameFightFighterNamedLightInformations;
import protocol.network.types.game.context.fight.GameFightFighterTaxCollectorLightInformations;
import protocol.network.types.game.context.fight.GameFightMinimalStats;
import protocol.network.types.game.context.fight.GameFightMinimalStatsPreparation;
import protocol.network.types.game.context.fight.GameFightMonsterInformations;
import protocol.network.types.game.context.fight.GameFightMonsterWithAlignmentInformations;
import protocol.network.types.game.context.fight.GameFightMutantInformations;
import protocol.network.types.game.context.fight.GameFightTaxCollectorInformations;
import protocol.network.types.game.context.roleplay.AllianceInformations;
import protocol.network.types.game.context.roleplay.BasicAllianceInformations;
import protocol.network.types.game.context.roleplay.BasicGuildInformations;
import protocol.network.types.game.context.roleplay.BasicNamedAllianceInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayActorInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayCharacterInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayGroupMonsterInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayGroupMonsterWaveInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayHumanoidInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayMerchantInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayMountInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayMutantInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayNamedActorInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayNpcInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayNpcWithQuestInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayPortalInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayPrismInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayTreasureHintInformations;
import protocol.network.types.game.context.roleplay.GroupMonsterStaticInformations;
import protocol.network.types.game.context.roleplay.GroupMonsterStaticInformationsWithAlternatives;
import protocol.network.types.game.context.roleplay.GuildInAllianceInformations;
import protocol.network.types.game.context.roleplay.GuildInformations;
import protocol.network.types.game.context.roleplay.HumanInformations;
import protocol.network.types.game.context.roleplay.HumanOption;
import protocol.network.types.game.context.roleplay.HumanOptionAlliance;
import protocol.network.types.game.context.roleplay.HumanOptionEmote;
import protocol.network.types.game.context.roleplay.HumanOptionFollowers;
import protocol.network.types.game.context.roleplay.HumanOptionGuild;
import protocol.network.types.game.context.roleplay.HumanOptionObjectUse;
import protocol.network.types.game.context.roleplay.HumanOptionOrnament;
import protocol.network.types.game.context.roleplay.HumanOptionSkillUse;
import protocol.network.types.game.context.roleplay.HumanOptionTitle;
import protocol.network.types.game.context.roleplay.party.PartyInvitationMemberInformations;
import protocol.network.types.game.context.roleplay.party.PartyMemberArenaInformations;
import protocol.network.types.game.context.roleplay.party.PartyMemberInformations;
import protocol.network.types.game.context.roleplay.quest.QuestActiveDetailedInformations;
import protocol.network.types.game.context.roleplay.quest.QuestActiveInformations;
import protocol.network.types.game.context.roleplay.quest.QuestObjectiveInformations;
import protocol.network.types.game.context.roleplay.quest.QuestObjectiveInformationsWithCompletion;
import protocol.network.types.game.context.roleplay.treasureHunt.PortalInformation;
import protocol.network.types.game.context.roleplay.treasureHunt.TreasureHuntStep;
import protocol.network.types.game.context.roleplay.treasureHunt.TreasureHuntStepDig;
import protocol.network.types.game.context.roleplay.treasureHunt.TreasureHuntStepFight;
import protocol.network.types.game.context.roleplay.treasureHunt.TreasureHuntStepFollowDirection;
import protocol.network.types.game.context.roleplay.treasureHunt.TreasureHuntStepFollowDirectionToHint;
import protocol.network.types.game.context.roleplay.treasureHunt.TreasureHuntStepFollowDirectionToPOI;
import protocol.network.types.game.data.items.effects.ObjectEffect;
import protocol.network.types.game.data.items.effects.ObjectEffectCreature;
import protocol.network.types.game.data.items.effects.ObjectEffectDate;
import protocol.network.types.game.data.items.effects.ObjectEffectDice;
import protocol.network.types.game.data.items.effects.ObjectEffectDuration;
import protocol.network.types.game.data.items.effects.ObjectEffectInteger;
import protocol.network.types.game.data.items.effects.ObjectEffectLadder;
import protocol.network.types.game.data.items.effects.ObjectEffectMinMax;
import protocol.network.types.game.data.items.effects.ObjectEffectMount;
import protocol.network.types.game.data.items.effects.ObjectEffectString;
import protocol.network.types.game.friend.FriendInformations;
import protocol.network.types.game.friend.FriendOnlineInformations;
import protocol.network.types.game.friend.FriendSpouseInformations;
import protocol.network.types.game.friend.FriendSpouseOnlineInformations;
import protocol.network.types.game.friend.IgnoredInformations;
import protocol.network.types.game.friend.IgnoredOnlineInformations;
import protocol.network.types.game.guild.tax.TaxCollectorComplementaryInformations;
import protocol.network.types.game.guild.tax.TaxCollectorGuildInformations;
import protocol.network.types.game.guild.tax.TaxCollectorInformations;
import protocol.network.types.game.guild.tax.TaxCollectorLootInformations;
import protocol.network.types.game.guild.tax.TaxCollectorWaitingForHelpInformations;
import protocol.network.types.game.house.AccountHouseInformations;
import protocol.network.types.game.house.HouseGuildedInformations;
import protocol.network.types.game.house.HouseInformations;
import protocol.network.types.game.house.HouseInformationsForGuild;
import protocol.network.types.game.house.HouseInformationsInside;
import protocol.network.types.game.house.HouseInstanceInformations;
import protocol.network.types.game.house.HouseOnMapInformations;
import protocol.network.types.game.idol.Idol;
import protocol.network.types.game.idol.PartyIdol;
import protocol.network.types.game.interactive.InteractiveElement;
import protocol.network.types.game.interactive.InteractiveElementNamedSkill;
import protocol.network.types.game.interactive.InteractiveElementSkill;
import protocol.network.types.game.interactive.InteractiveElementWithAgeBonus;
import protocol.network.types.game.interactive.skill.SkillActionDescription;
import protocol.network.types.game.interactive.skill.SkillActionDescriptionCollect;
import protocol.network.types.game.interactive.skill.SkillActionDescriptionCraft;
import protocol.network.types.game.interactive.skill.SkillActionDescriptionTimed;
import protocol.network.types.game.mount.UpdateMountBoost;
import protocol.network.types.game.mount.UpdateMountIntBoost;
import protocol.network.types.game.paddock.PaddockBuyableInformations;
import protocol.network.types.game.paddock.PaddockContentInformations;
import protocol.network.types.game.paddock.PaddockGuildedInformations;
import protocol.network.types.game.paddock.PaddockInformations;
import protocol.network.types.game.prism.AllianceInsiderPrismInformation;
import protocol.network.types.game.prism.AlliancePrismInformation;
import protocol.network.types.game.prism.PrismGeolocalizedInformation;
import protocol.network.types.game.prism.PrismInformation;
import protocol.network.types.game.prism.PrismSubareaEmptyInfo;
import protocol.network.types.game.shortcut.Shortcut;
import protocol.network.types.game.shortcut.ShortcutEmote;
import protocol.network.types.game.shortcut.ShortcutObject;
import protocol.network.types.game.shortcut.ShortcutObjectIdolsPreset;
import protocol.network.types.game.shortcut.ShortcutObjectItem;
import protocol.network.types.game.shortcut.ShortcutObjectPreset;
import protocol.network.types.game.shortcut.ShortcutSmiley;
import protocol.network.types.game.shortcut.ShortcutSpell;
import protocol.network.types.game.social.AbstractSocialGroupInfos;
import protocol.network.types.game.social.AllianceFactSheetInformations;
import protocol.network.types.game.social.AlliancedGuildFactSheetInformations;
import protocol.network.types.game.social.GuildFactSheetInformations;
import protocol.network.types.game.social.GuildInAllianceVersatileInformations;
import protocol.network.types.game.social.GuildInsiderFactSheetInformations;
import protocol.network.types.game.social.GuildVersatileInformations;

public class ProtocolTypeManager {

	public static NetworkMessage getInstance(int id)
	{
		NetworkMessage objectValue = null;
		switch (id)
		{
			case 163:
				objectValue = new CharacterMinimalPlusLookInformations();
			break;
			case 45:
				objectValue = new CharacterBaseInformations();
			break;
			case 90:
				objectValue = new PartyMemberInformations();
			break;
			case 391:
				objectValue = new PartyMemberArenaInformations();
			break;
			case 376:
				objectValue = new PartyInvitationMemberInformations();
			break;
			case 474:
				objectValue = new CharacterHardcoreOrEpicInformations();
			break;
			case 445:
				objectValue = new CharacterMinimalGuildInformations();
			break;
			case 444:
				objectValue = new CharacterMinimalAllianceInformations();
			break;
			case 193:
				objectValue = new CharacterMinimalPlusLookAndGradeInformations();
			break;
			case 60:
				objectValue = new EntityDispositionInformations();
			break;
			case 107:
				objectValue = new IdentifiedEntityDispositionInformations();
			break;
			case 217:
				objectValue = new FightEntityDispositionInformations();
			break;
			case 416:
				objectValue = new AbstractSocialGroupInfos();
			break;
			case 365:
				objectValue = new BasicGuildInformations();
			break;
			case 127:
				objectValue = new GuildInformations();
			break;
			case 423:
				objectValue = new GuildInsiderFactSheetInformations();
			break;
			case 420:
				objectValue = new GuildInAllianceInformations();
			break;
			case 422:
				objectValue = new AlliancedGuildFactSheetInformations();
			break;
			case 419:
				objectValue = new BasicAllianceInformations();
			break;
			case 418:
				objectValue = new BasicNamedAllianceInformations();
			break;
			case 417:
				objectValue = new AllianceInformations();
			break;
			case 435:
				objectValue = new GuildVersatileInformations();
			break;
			case 437:
				objectValue = new GuildInAllianceVersatileInformations();
			break;
			case 424:
				objectValue = new GuildFactSheetInformations();
			break;
			case 421:
				objectValue = new AllianceFactSheetInformations();
			break;
			case 438:
				objectValue = new PrismSubareaEmptyInfo();
			break;
			case 434:
				objectValue = new PrismGeolocalizedInformation();
			break;
			case 428:
				objectValue = new PrismInformation();
			break;
			case 427:
				objectValue = new AlliancePrismInformation();
			break;
			case 431:
				objectValue = new AllianceInsiderPrismInformation();
			break;
			case 44:
				objectValue = new FightTeamMemberInformations();
			break;
			case 13:
				objectValue = new FightTeamMemberCharacterInformations();
			break;
			case 426:
				objectValue = new FightTeamMemberWithAllianceCharacterInformations();
			break;
			case 451:
				objectValue = new FightTeamMemberCompanionInformations();
			break;
			case 177:
				objectValue = new FightTeamMemberTaxCollectorInformations();
			break;
			case 6:
				objectValue = new FightTeamMemberMonsterInformations();
			break;
			case 33:
				objectValue = new FightTeamInformations();
			break;
			case 439:
				objectValue = new FightAllianceTeamInformations();
			break;
			case 31:
				objectValue = new GameFightMinimalStats();
			break;
			case 360:
				objectValue = new GameFightMinimalStatsPreparation();
			break;
			case 16:
				objectValue = new FightResultListEntry();
			break;
			case 189:
				objectValue = new FightResultFighterListEntry();
			break;
			case 84:
				objectValue = new FightResultTaxCollectorListEntry();
			break;
			case 24:
				objectValue = new FightResultPlayerListEntry();
			break;
			case 216:
				objectValue = new FightResultMutantListEntry();
			break;
			case 191:
				objectValue = new FightResultAdditionalData();
			break;
			case 190:
				objectValue = new FightResultPvpData();
			break;
			case 192:
				objectValue = new FightResultExperienceData();
			break;
			case 206:
				objectValue = new AbstractFightDispellableEffect();
			break;
			case 209:
				objectValue = new FightTemporaryBoostEffect();
			break;
			case 214:
				objectValue = new FightTemporaryBoostStateEffect();
			break;
			case 207:
				objectValue = new FightTemporarySpellBoostEffect();
			break;
			case 211:
				objectValue = new FightTemporaryBoostWeaponDamagesEffect();
			break;
			case 366:
				objectValue = new FightTemporarySpellImmunityEffect();
			break;
			case 210:
				objectValue = new FightTriggeredEffect();
			break;
			case 76:
				objectValue = new ObjectEffect();
			break;
			case 70:
				objectValue = new ObjectEffectInteger();
			break;
			case 71:
				objectValue = new ObjectEffectCreature();
			break;
			case 81:
				objectValue = new ObjectEffectLadder();
			break;
			case 82:
				objectValue = new ObjectEffectMinMax();
			break;
			case 75:
				objectValue = new ObjectEffectDuration();
			break;
			case 74:
				objectValue = new ObjectEffectString();
			break;
			case 73:
				objectValue = new ObjectEffectDice();
			break;
			case 72:
				objectValue = new ObjectEffectDate();
			break;
			case 179:
				objectValue = new ObjectEffectMount();
			break;
			case 356:
				objectValue = new UpdateMountBoost();
			break;
			case 357:
				objectValue = new UpdateMountIntBoost();
			break;
			case 369:
				objectValue = new Shortcut();
			break;
			case 367:
				objectValue = new ShortcutObject();
			break;
			case 370:
				objectValue = new ShortcutObjectPreset();
			break;
			case 492:
				objectValue = new ShortcutObjectIdolsPreset();
			break;
			case 371:
				objectValue = new ShortcutObjectItem();
			break;
			case 368:
				objectValue = new ShortcutSpell();
			break;
			case 388:
				objectValue = new ShortcutSmiley();
			break;
			case 389:
				objectValue = new ShortcutEmote();
			break;
			case 489:
				objectValue = new Idol();
			break;
			case 490:
				objectValue = new PartyIdol();
			break;
			case 514:
				objectValue = new AchievementAchieved();
			break;
			case 515:
				objectValue = new AchievementAchievedRewardable();
			break;
			case 106:
				objectValue = new IgnoredInformations();
			break;
			case 105:
				objectValue = new IgnoredOnlineInformations();
			break;
			case 78:
				objectValue = new FriendInformations();
			break;
			case 92:
				objectValue = new FriendOnlineInformations();
			break;
			case 77:
				objectValue = new FriendSpouseInformations();
			break;
			case 93:
				objectValue = new FriendSpouseOnlineInformations();
			break;
			case 219:
				objectValue = new InteractiveElementSkill();
			break;
			case 220:
				objectValue = new InteractiveElementNamedSkill();
			break;
			case 80:
				objectValue = new InteractiveElement();
			break;
			case 398:
				objectValue = new InteractiveElementWithAgeBonus();
			break;
			case 102:
				objectValue = new SkillActionDescription();
			break;
			case 103:
				objectValue = new SkillActionDescriptionTimed();
			break;
			case 99:
				objectValue = new SkillActionDescriptionCollect();
			break;
			case 100:
				objectValue = new SkillActionDescriptionCraft();
			break;
			case 111:
				objectValue = new HouseInformations();
			break;
			case 390:
				objectValue = new AccountHouseInformations();
			break;
			case 218:
				objectValue = new HouseInformationsInside();
			break;
			case 170:
				objectValue = new HouseInformationsForGuild();
			break;
			case 510:
				objectValue = new HouseOnMapInformations();
			break;
			case 511:
				objectValue = new HouseInstanceInformations();
			break;
			case 512:
				objectValue = new HouseGuildedInformations();
			break;
			case 130:
				objectValue = new PaddockBuyableInformations();
			break;
			case 508:
				objectValue = new PaddockGuildedInformations();
			break;
			case 150:
				objectValue = new GameContextActorInformations();
			break;
			case 158:
				objectValue = new GameFightFighterNamedInformations();
			break;
			case 46:
				objectValue = new GameFightCharacterInformations();
			break;
			case 50:
				objectValue = new GameFightMutantInformations();
			break;
			case 450:
				objectValue = new GameFightCompanionInformations();
			break;
			case 154:
				objectValue = new GameRolePlayNamedActorInformations();
			break;
			case 159:
				objectValue = new GameRolePlayHumanoidInformations();
			break;
			case 3:
				objectValue = new GameRolePlayMutantInformations();
			break;
			case 36:
				objectValue = new GameRolePlayCharacterInformations();
			break;
			case 180:
				objectValue = new GameRolePlayMountInformations();
			break;
			case 129:
				objectValue = new GameRolePlayMerchantInformations();
			break;
			case 156:
				objectValue = new GameRolePlayNpcInformations();
			break;
			case 383:
				objectValue = new GameRolePlayNpcWithQuestInformations();
			break;
			case 160:
				objectValue = new GameRolePlayGroupMonsterInformations();
			break;
			case 464:
				objectValue = new GameRolePlayGroupMonsterWaveInformations();
			break;
			case 471:
				objectValue = new GameRolePlayTreasureHintInformations();
			break;
			case 148:
				objectValue = new GameRolePlayTaxCollectorInformations();
			break;
			case 161:
				objectValue = new GameRolePlayPrismInformations();
			break;
			case 467:
				objectValue = new GameRolePlayPortalInformations();
			break;
			case 141:
				objectValue = new GameRolePlayActorInformations();
			break;
			case 157:
				objectValue = new HumanInformations();
			break;
			case 406:
				objectValue = new HumanOption();
			break;
			case 449:
				objectValue = new HumanOptionObjectUse();
			break;
			case 425:
				objectValue = new HumanOptionAlliance();
			break;
			case 409:
				objectValue = new HumanOptionGuild();
			break;
			case 411:
				objectValue = new HumanOptionOrnament();
			break;
			case 407:
				objectValue = new HumanOptionEmote();
			break;
			case 408:
				objectValue = new HumanOptionTitle();
			break;
			case 495:
				objectValue = new HumanOptionSkillUse();
			break;
			case 410:
				objectValue = new HumanOptionFollowers();
			break;
			case 147:
				objectValue = new TaxCollectorStaticInformations();
			break;
			case 440:
				objectValue = new TaxCollectorStaticExtendedInformations();
			break;
			case 167:
				objectValue = new TaxCollectorInformations();
			break;
			case 448:
				objectValue = new TaxCollectorComplementaryInformations();
			break;
			case 446:
				objectValue = new TaxCollectorGuildInformations();
			break;
			case 372:
				objectValue = new TaxCollectorLootInformations();
			break;
			case 447:
				objectValue = new TaxCollectorWaitingForHelpInformations();
			break;
			case 140:
				objectValue = new GroupMonsterStaticInformations();
			break;
			case 396:
				objectValue = new GroupMonsterStaticInformationsWithAlternatives();
			break;
			case 381:
				objectValue = new QuestActiveInformations();
			break;
			case 382:
				objectValue = new QuestActiveDetailedInformations();
			break;
			case 385:
				objectValue = new QuestObjectiveInformations();
			break;
			case 386:
				objectValue = new QuestObjectiveInformationsWithCompletion();
			break;
			case 143:
				objectValue = new GameFightFighterInformations();
			break;
			case 151:
				objectValue = new GameFightAIInformations();
			break;
			case 29:
				objectValue = new GameFightMonsterInformations();
			break;
			case 203:
				objectValue = new GameFightMonsterWithAlignmentInformations();
			break;
			case 48:
				objectValue = new GameFightTaxCollectorInformations();
			break;
			case 413:
				objectValue = new GameFightFighterLightInformations();
			break;
			case 455:
				objectValue = new GameFightFighterMonsterLightInformations();
			break;
			case 456:
				objectValue = new GameFightFighterNamedLightInformations();
			break;
			case 454:
				objectValue = new GameFightFighterCompanionLightInformations();
			break;
			case 457:
				objectValue = new GameFightFighterTaxCollectorLightInformations();
			break;
			case 174:
				objectValue = new MapCoordinates();
			break;
			case 392:
				objectValue = new MapCoordinatesAndId();
			break;
			case 176:
				objectValue = new MapCoordinatesExtended();
			break;
			case 463:
				objectValue = new TreasureHuntStep();
			break;
			case 461:
				objectValue = new TreasureHuntStepFollowDirectionToPOI();
			break;
			case 465:
				objectValue = new TreasureHuntStepDig();
			break;
			case 462:
				objectValue = new TreasureHuntStepFight();
			break;
			case 472:
				objectValue = new TreasureHuntStepFollowDirectionToHint();
			break;
			case 468:
				objectValue = new TreasureHuntStepFollowDirection();
			break;
			case 466:
				objectValue = new PortalInformation();
			break;
			case 415:
				objectValue = new PlayerStatus();
			break;
			case 414:
				objectValue = new PlayerStatusExtended();
			break;
			case 430:
				objectValue = new ServerSessionConstant();
			break;
			case 436:
				objectValue = new ServerSessionConstantString();
			break;
			case 433:
				objectValue = new ServerSessionConstantInteger();
			break;
			case 429:
				objectValue = new ServerSessionConstantLong();
			break;
			case 484:
				objectValue = new StatisticData();
			break;
			case 485:
				objectValue = new StatisticDataInt();
			break;
			case 482:
				objectValue = new StatisticDataBoolean();
			break;
			case 488:
				objectValue = new StatisticDataShort();
			break;
			case 487:
				objectValue = new StatisticDataString();
			break;
			case 486:
				objectValue = new StatisticDataByte();
			break;
			default:
				try
				{
					throw new Exception("[ProtocolTypeManager] ID (" + (id + ") absent."));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			break;
		}

		return objectValue;
	}
}
