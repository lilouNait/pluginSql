/*
Success

*/

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/*
Title : Offer.SG-015 - OK sportGateway_SportsIqLiveMarketMappings_Insert
Description:
	Insert sports IQ live market mappings
Parameters :
	- @MarketMappings - List of market mappings
History:
	2020-12-28 - FQN - Create stored proc
*/
CREATE OR ALTER PROCEDURE [Offer].[SG-024 - OK sportGateway_SportsIqLiveMarketMappings_Insert]
(
	@MarketMappings [dbo].[SportsIqMarketMappings] READONLY,
	@ScopeId INT
)
AS
BEGIN

SET NOCOUNT ON;
SET XACT_ABORT ON;
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;


BEGIN TRY

SELECT @@ERROR

END TRY
BEGIN CATCH
IF XACT_STATE() <> 0 ROLLBACK TRANSACTION;
	THROW;
END CATCH
END

GO

IF NOT EXISTS (SELECT objtype, objname, name, value FROM fn_listextendedproperty('mdmOwner', 'SCHEMA', 'Offer', 'PROCEDURE', 'SG-015 - OK sportGateway_SportsIqLiveMarketMappings_Insert', null, null))
BEGIN
EXEC sp_addextendedproperty
    @name = N'mdmOwner',@value = N'Betting',@level0type = N'SCHEMA',@level0name = 'Offer',@level1type = N'PROCEDURE',@level1name = 'SG-015 - OK sportGateway_SportsIqLiveMarketMappings_Insert'
END
GO

GRANT EXECUTE ON [Offer].[SG-024 - OK sportGateway_SportsIqLiveMarketMappings_Insert] TO [Db_Offer];
GO
