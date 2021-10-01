/*
Failure : missing statements

SET NOCOUNT ON;
SET XACT_ABORT ON;
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;



*/

SET
ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/*
Title : Offer.SG-003 - KO sportGateway_SportsIqLiveMarketMappings_Insert
Description:
	Insert sports IQ live market mappings
Parameters :
	- @MarketMappings - List of market mappings
History:
	2020-12-28 - FQN - Create stored proc
*/
CREATE
OR ALTER PROCEDURE
[Offer].[SG-003 - KO sportGateway_SportsIqLiveMarketMappings_Insert]
(
	@MarketMappings [dbo].[SportsIqMarketMappings] READONLY,
	@ScopeId INT
)
AS
BEGIN



BEGIN TRY

	DECLARE
@result
	TABLE(
		MappingId INT NOT NULL,
		SportsIqMarketTypeId INT NOT NULL,
		SportsIqMatchId INT NOT NULL,
		BetclicMarketId INT NOT NULL,
		Specifier VARCHAR(50) NULL
	)

	INSERT INTO SportsIqLiveMarketMappings(SportsIqMarketTypeId, SportsIqMatchId, Specifier, ScopeId)
SELECT SportsIqMarketTypeId, SportsIqMatchId, Specifier, @ScopeId
FROM @MarketMappings mm
WHERE NOT EXISTS(SELECT 1
                 FROM SportsIqLiveMarketMappings ilm
                 WHERE ilm.ScopeId = @ScopeId
                   AND ilm.SportsIqMarketTypeId = mm.SportsIqMarketTypeId
                   AND ilm.SportsIqMatchId = mm.SportsIqMatchId
                   AND ISNULL(ilm.Specifier, '') = ISNULL(mm.Specifier, ''))
    INSERT
INTO @result(MappingId, BetclicMarketId, SportsIqMarketTypeId, SportsIqMatchId, Specifier)
SELECT ilm.Id, mm.BetclicMarketId, mm.SportsIqMarketTypeId, mm.SportsIqMatchId, mm.Specifier
FROM SportsIqLiveMarketMappings ilm
         INNER JOIN @MarketMappings mm
                    ON ilm.ScopeId = @ScopeId
                        AND ilm.SportsIqMarketTypeId = mm.SportsIqMarketTypeId
                        AND ilm.SportsIqMatchId = mm.SportsIqMatchId
                        AND ISNULL(ilm.Specifier, '') = ISNULL(mm.Specifier, '')

     -- PK conflicts in this statement should not be avoided. It means there is actual problem on caller side
    INSERT
INTO TosLiveParis(ExternalId, LivePariId, TosApplicationExternalId, TosApplicationRefererId)
SELECT r.MappingId, r.BetclicMarketId, @ScopeId, 16
FROM @result r

SELECT r.BetclicMarketId,
       r.MappingId,
       r.SportsIqMarketTypeId,
       r.SportsIqMatchId,
       r.Specifier
FROM @result r

END TRY
BEGIN CATCH
IF XACT_STATE() <> 0 ROLLBACK TRANSACTION;
	THROW;
END CATCH
END

GO

IF NOT EXISTS (SELECT objtype, objname, name, value FROM fn_listextendedproperty('mdmOwner', 'SCHEMA', 'Offer', 'PROCEDURE', 'SG-003 - KO sportGateway_SportsIqLiveMarketMappings_Insert', null, null))
BEGIN
EXEC sp_addextendedproperty
    @name = N'mdmOwner',@value = N'Betting',@level0type = N'SCHEMA',@level0name = 'Offer',@level1type = N'PROCEDURE',@level1name = 'SG-003 - KO sportGateway_SportsIqLiveMarketMappings_Insert'
END
GO

GRANT EXECUTE ON [Offer].[SG-003 - KO sportGateway_SportsIqLiveMarketMappings_Insert] TO [Db_Offer];
GO
